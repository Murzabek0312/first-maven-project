package com.mentor.dmdev.entity;

import com.mentor.dmdev.enums.Genre;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MoviesActorTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void prepare() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @Test
    void shouldSaveMovieActor() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var actor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(actor);

        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        session.save(director);

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();
        session.save(subscription);

        var movie = Movie.builder()
                .name("movieName")
                .director(director)
                .releaseDate(LocalDate.of(2022, 2, 12))
                .country("country")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(movie);

        var moviesActor = MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();

        var actualResult = session.save(moviesActor);

        assertNotNull(actualResult);

        session.getTransaction().rollback();
    }

    @Test
    void shouldGetMovieActor() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var actor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(actor);

        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        session.save(director);

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();
        session.save(subscription);

        var movie = Movie.builder()
                .name("movieName")
                .director(director)
                .releaseDate(LocalDate.of(2022, 2, 12))
                .country("country")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(movie);

        var moviesActor = MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();

        var movieActorId = session.save(moviesActor);
        session.flush();
        session.clear();

        MoviesActor actualResult = session.get(MoviesActor.class, movieActorId);

        assertEquals(actor, actualResult.getActor());
        assertEquals(movie, actualResult.getMovie());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateMovieActor() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var actor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(actor);

        var newActor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(newActor);

        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        session.save(director);

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();
        session.save(subscription);

        var movie = Movie.builder()
                .name("movieName")
                .director(director)
                .releaseDate(LocalDate.of(2022, 2, 12))
                .country("country")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(movie);

        var moviesActor = MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();

        var movieActorId = session.save(moviesActor);
        session.flush();
        session.clear();

        MoviesActor currentEntity = session.get(MoviesActor.class, movieActorId);

        currentEntity.setActor(newActor);

        session.update(currentEntity);
        session.flush();
        session.clear();

        MoviesActor actualResult = session.get(MoviesActor.class, movieActorId);

        assertEquals(newActor, actualResult.getActor());
        assertEquals(movie, actualResult.getMovie());

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteMovieActor() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var actor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(actor);

        var newActor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(newActor);

        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        session.save(director);

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();
        session.save(subscription);

        var movie = Movie.builder()
                .name("movieName")
                .director(director)
                .releaseDate(LocalDate.of(2022, 2, 12))
                .country("country")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(movie);

        var moviesActor = MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();

        var movieActorId = session.save(moviesActor);
        session.flush();

        session.remove(moviesActor);
        session.flush();

        MoviesActor actualResult = session.get(MoviesActor.class, movieActorId);

        assertNull(actualResult);
        session.getTransaction().rollback();
    }

}