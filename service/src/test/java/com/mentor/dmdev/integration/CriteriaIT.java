package com.mentor.dmdev.integration;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.dto.ActorFilter;
import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.entity.Actor_;
import com.mentor.dmdev.entity.Movie_;
import com.mentor.dmdev.entity.MoviesActor_;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CriteriaIT extends BaseIT {

    @Test
    void findAllActor() {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Actor.class);

        Root<Actor> actor = criteria.from(Actor.class);
        criteria.select(actor);

        List<Actor> actualResult = session.createQuery(criteria).list();
        assertEquals(7, actualResult.size());
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
        assertEquals("Tarantino", actualResult.get(0).getSecondname());
        assertEquals("NeTarantino", actualResult.get(1).getSecondname());
    }
}