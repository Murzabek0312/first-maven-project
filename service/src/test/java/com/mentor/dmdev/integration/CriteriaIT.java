package com.mentor.dmdev.integration;

import com.mentor.dmdev.dto.ActorFilter;
import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.entity.Actor_;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.Movie_;
import com.mentor.dmdev.entity.MoviesActor;
import com.mentor.dmdev.entity.MoviesActor_;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.Genre;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CriteriaIT {

    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    static void prepare() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    void initDb() {
        session = sessionFactory.openSession();
        session.beginTransaction();

        Subscription subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        Actor director = Actor.builder()
                .firstname("Quentin")
                .secondname("Tarantino")
                .birthDate(LocalDate.of(1963, 3, 27))
                .biography("Cool boy")
                .build();

        Movie movie = Movie.builder()
                .name("The Hateful Eight")
                .director(director)
                .country("USA")
                .genre(Genre.ACTION)
                .subscription(subscription)
                .releaseDate(LocalDate.of(2016, 1, 1))
                .build();

        MoviesActor moviesActor1 = new MoviesActor();
        MoviesActor moviesActor2 = new MoviesActor();


        var actor1 = Actor.builder()
                .firstname("Petr")
                .secondname("actorSecondName1")
                .birthDate(LocalDate.of(2012, 7, 5))
                .biography("actorBiography1")
                .build();

        var actor2 = Actor.builder()
                .firstname("Petr")
                .secondname("actorSecondName2")
                .birthDate(LocalDate.of(2013, 10, 14))
                .biography("actorBiography2")
                .build();

        var actor3 = Actor.builder()
                .firstname("Dmitriy")
                .secondname("astorSecondName3")
                .birthDate(LocalDate.of(2012, 5, 27))
                .biography("actorBiography3")
                .build();

        Actor actor4 = Actor.builder()
                .firstname("Quentin")
                .secondname("NeTarantino")
                .birthDate(LocalDate.of(1966, 4, 30))
                .biography("Bad boy")
                .build();

        moviesActor1.setActor(actor1);
        moviesActor1.setMovie(movie);
        moviesActor2.setActor(actor2);
        moviesActor2.setMovie(movie);

        session.save(subscription);
        session.save(director);
        session.save(movie);
        session.save(actor1);
        session.save(actor2);
        session.save(moviesActor1);
        session.save(moviesActor2);
        session.save(actor3);
        session.save(actor4);


        session.flush();
        session.clear();
    }

    @Test
    void findAllActor() {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Actor.class);

        Root<Actor> actor = criteria.from(Actor.class);
        criteria.select(actor);

        List<Actor> actualResult = session.createQuery(criteria).list();
        assertEquals(5, actualResult.size());
    }

    @Test
    void findActorByFirstname() {
        var firstname = "Petr";

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Actor.class);
        var actor = criteria.from(Actor.class);

        criteria.select(actor).where(
                cb.equal(actor.get(Actor_.FIRSTNAME), firstname)
        );

        List<Actor> actualResult = session.createQuery(criteria).list();
        assertEquals(2, actualResult.size());
    }

    @Test
    void findLimitedActorsOrderByBirthday() {
        var limit = 2;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Actor> criteria = cb.createQuery(Actor.class);
        Root<Actor> actor = criteria.from(Actor.class);
        criteria.select(actor).orderBy(cb.asc(actor.get(Actor_.birthDate)));

        List<Actor> actualResult = session.createQuery(criteria)
                .setMaxResults(limit)
                .list();
        assertEquals(2, actualResult.size());
        assertEquals("Tarantino", actualResult.get(0).getSecondname());
        assertEquals("NeTarantino", actualResult.get(1).getSecondname());
    }

    @Test
    void findAllActorsByMovie() {
        var movieName = "The Hateful Eight";
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Actor.class);
        var actor = criteria.from(Actor.class);
        var moviesActors = actor.join(Actor_.MOVIES_ACTORS);
        var movie = moviesActors.join(MoviesActor_.MOVIE);

        criteria.select(actor).where(
                cb.equal(movie.get(Movie_.NAME), movieName)
        );

        var actualResult = session.createQuery(criteria).list();

        assertEquals(2, actualResult.size());
        assertEquals("actorSecondName1", actualResult.get(0).getSecondname());
        assertEquals("actorSecondName2", actualResult.get(1).getSecondname());
    }

    @Test
    void findActorByFilterCriteria() {
        var cb = session.getCriteriaBuilder();

        ActorFilter filter = ActorFilter.builder()
                .firstname("Quentin")
                .build();

        var criteria = cb.createQuery(Actor.class);
        var actor = criteria.from(Actor.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFirstname() != null) {
            predicates.add(cb.equal(actor.get(Actor_.FIRSTNAME), filter.getFirstname()));
        }
        if (filter.getSecondname() != null) {
            predicates.add(cb.equal(actor.get(Actor_.SECONDNAME), filter.getSecondname()));
        }

        criteria.select(actor).where(predicates.toArray(Predicate[]::new));

        List<Actor> actualResult = session.createQuery(criteria).list();

        actualResult.forEach(ac -> ac.getMoviesActors().size());

        assertEquals(2, actualResult.size());
        assertEquals("Tarantino", actualResult.get(0).getSecondname());
        assertEquals("NeTarantino", actualResult.get(1).getSecondname());
    }

    @Test
    void findActorByFilterCriteriaOptimizedByEntityGraph() {
        var cb = session.getCriteriaBuilder();

        ActorFilter filter = ActorFilter.builder()
                .firstname("Quentin")
                .build();

        var criteria = cb.createQuery(Actor.class);
        var actor = criteria.from(Actor.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFirstname() != null) {
            predicates.add(cb.equal(actor.get(Actor_.FIRSTNAME), filter.getFirstname()));
        }
        if (filter.getSecondname() != null) {
            predicates.add(cb.equal(actor.get(Actor_.SECONDNAME), filter.getSecondname()));
        }

        criteria.select(actor).where(predicates.toArray(Predicate[]::new));

        RootGraph<Actor> actorGraph = session.createEntityGraph(Actor.class);
        actorGraph.addAttributeNodes("moviesActors");

        List<Actor> actualResult = session.createQuery(criteria)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), actorGraph)
                .list();

        actualResult.forEach(ac -> ac.getMoviesActors().size());

        assertEquals(2, actualResult.size());
        assertEquals("NeTarantino", actualResult.get(0).getSecondname());
        assertEquals("Tarantino", actualResult.get(1).getSecondname());
    }

    @AfterEach
    void clear() {
        session.getTransaction().rollback();
        session.close();
    }

    @AfterAll
    static void clearAll() {
        sessionFactory.close();
    }
}