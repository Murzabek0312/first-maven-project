package com.mentor.dmdev.integration;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.dao.QPredicate;
import com.mentor.dmdev.dto.ActorFilter;
import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.MoviesActor;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.Genre;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static com.mentor.dmdev.entity.QActor.actor;
import static com.mentor.dmdev.entity.QMovie.movie;
import static com.mentor.dmdev.entity.QMoviesActor.moviesActor;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryDslIT extends BaseIT {

    @BeforeEach
    void initDb() {
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
    void findAllActors() {
        List<Actor> actualResult = new JPAQuery<Actor>(session)
                .select(actor)
                .from(actor)
                .fetch();

        assertEquals(5, actualResult.size());
    }

    @Test
    void findActorByFirstname() {
        var firstname = "Petr";

        List<Actor> actualResult = new JPAQuery<Actor>(session)
                .select(actor)
                .from(actor)
                .where(actor.firstname.eq(firstname))
                .fetch();

        assertEquals(2, actualResult.size());
    }

    @Test
    void findLimitedActorsOrderByBirthday() {
        var limit = 2;
        List<Actor> actualResult = new JPAQuery<Actor>(session)
                .select(actor)
                .from(actor)
                .limit(limit)
                .orderBy(actor.birthDate.asc())
                .fetch();

        assertEquals(2, actualResult.size());
        assertEquals("Tarantino", actualResult.get(0).getSecondname());
        assertEquals("NeTarantino", actualResult.get(1).getSecondname());
    }

    @Test
    void findAllActorsByMovie() {
        var movieName = "The Hateful Eight";

        List<Actor> actualResult = new JPAQuery<Actor>(session)
                .select(actor)
                .from(actor)
                .join(actor.moviesActors, moviesActor)
                .join(moviesActor.movie, movie)
                .where(movie.name.eq(movieName))
                .fetch();

        assertEquals(2, actualResult.size());
        assertEquals("actorSecondName1", actualResult.get(0).getSecondname());
        assertEquals("actorSecondName2", actualResult.get(1).getSecondname());
    }

    @Test
    void findActorByFilterQueryDsl() {
        ActorFilter filter = ActorFilter.builder()
                .firstname("Quentin")
                .secondname("NeTarantino")
                .build();

        Predicate predicate = QPredicate.builder()
                .add(filter.getFirstname(), actor.firstname::eq)
                .add(filter.getSecondname(), actor.secondname::eq)
                .buildOr();

        List<Actor> actualResult = new JPAQuery<Actor>(session)
                .select(actor)
                .from(actor)
                .where(predicate)
                .fetch();

        actualResult.forEach(ac -> ac.getMoviesActors().size());

        assertEquals(2, actualResult.size());
        assertEquals("Quentin", actualResult.get(0).getFirstname());
        assertEquals("NeTarantino", actualResult.get(1).getSecondname());
    }

    @Test
    void findActorByFilterQueryDslOptimizedByEntityGraph() {
        ActorFilter filter = ActorFilter.builder()
                .firstname("Quentin")
                .secondname("NeTarantino")
                .build();

        Predicate predicate = QPredicate.builder()
                .add(filter.getFirstname(), actor.firstname::eq)
                .add(filter.getSecondname(), actor.secondname::eq)
                .buildOr();

        RootGraph<Actor> actorGraph = session.createEntityGraph(Actor.class);
        actorGraph.addAttributeNodes("moviesActors");

        List<Actor> actualResult = new JPAQuery<Actor>(session)
                .select(actor)
                .from(actor)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), actorGraph)
                .fetch();

        actualResult.forEach(ac -> ac.getMoviesActors().size());

        assertEquals(2, actualResult.size());
        assertEquals("NeTarantino", actualResult.get(0).getSecondname());
        assertEquals("Tarantino", actualResult.get(1).getSecondname());
    }
}