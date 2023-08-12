package com.mentor.dmdev.integration;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.dto.ActorFilter;
import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.repository.QPredicate;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityGraph;
import java.util.List;

import static com.mentor.dmdev.entity.QActor.actor;
import static com.mentor.dmdev.entity.QMovie.movie;
import static com.mentor.dmdev.entity.QMoviesActor.moviesActor;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryDslIT extends BaseIT {

    @Test
    void findAllActors() {
        List<Actor> actualResult = new JPAQuery<Actor>(entityManager)
                .select(actor)
                .from(actor)
                .fetch();

        assertEquals(7, actualResult.size());
    }

    @Test
    void findActorByFirstname() {
        var firstname = "Petr";

        List<Actor> actualResult = new JPAQuery<Actor>(entityManager)
                .select(actor)
                .from(actor)
                .where(actor.firstname.eq(firstname))
                .fetch();

        assertEquals(2, actualResult.size());
    }

    @Test
    void findLimitedActorsOrderByBirthday() {
        var limit = 2;
        List<Actor> actualResult = new JPAQuery<Actor>(entityManager)
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

        List<Actor> actualResult = new JPAQuery<Actor>(entityManager)
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

        List<Actor> actualResult = new JPAQuery<Actor>(entityManager)
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

        EntityGraph<Actor> actorGraph = entityManager.createEntityGraph(Actor.class);
        actorGraph.addAttributeNodes("moviesActors");

        List<Actor> actualResult = new JPAQuery<Actor>(entityManager)
                .select(actor)
                .from(actor)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), actorGraph)
                .fetch();

        actualResult.forEach(ac -> ac.getMoviesActors().size());

        assertEquals(2, actualResult.size());
        assertEquals("Tarantino", actualResult.get(0).getSecondname());
        assertEquals("NeTarantino", actualResult.get(1).getSecondname());
    }
}