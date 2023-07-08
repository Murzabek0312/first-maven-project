package com.mentor.dmdev.entity;

import com.mentor.dmdev.enums.Genre;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MovieTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void prepare() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @Test
    void shouldSaveMovie() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        var movie = Movie.builder()
                .name("movieName1")
                .director(director)
                .releaseDate(LocalDate.of(2012, 12, 22))
                .country("country1")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(subscription);
        session.save(director);

        Serializable actualResult = session.save(movie);

        assertNotNull(actualResult);

        session.getTransaction().rollback();
    }

    @Test
    void shouldGetMovie() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        var movie = Movie.builder()
                .name("movieName1")
                .director(director)
                .releaseDate(LocalDate.of(2012, 12, 22))
                .country("country1")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(subscription);
        session.save(director);

        Serializable movieId = session.save(movie);
        session.flush();
        session.clear();

        Movie actualResult = session.get(Movie.class, movieId);

        assertEquals("movieName1", actualResult.getName());
        assertEquals(director, actualResult.getDirector());
        assertEquals(LocalDate.of(2012, 12, 22), actualResult.getReleaseDate());
        assertEquals("country1", actualResult.getCountry());
        assertEquals(Genre.TRILLER, actualResult.getGenre());
        assertEquals(subscription, actualResult.getSubscription());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateMovie() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        var movie = Movie.builder()
                .name("movieName1")
                .director(director)
                .releaseDate(LocalDate.of(2012, 12, 22))
                .country("country1")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(subscription);
        session.save(director);

        Serializable movieId = session.save(movie);
        session.flush();
        session.clear();

        Movie currentMovie = session.get(Movie.class, movieId);

        currentMovie.setName("new Name");
        currentMovie.setCountry("new Country");

        session.flush();
        session.clear();

        Movie actualResult = session.get(Movie.class, movieId);

        assertEquals("new Name", actualResult.getName());
        assertEquals(director, actualResult.getDirector());
        assertEquals(LocalDate.of(2012, 12, 22), actualResult.getReleaseDate());
        assertEquals("new Country", actualResult.getCountry());
        assertEquals(Genre.TRILLER, actualResult.getGenre());
        assertEquals(subscription, actualResult.getSubscription());

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteMovie() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        var subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();

        var movie = Movie.builder()
                .name("movieName1")
                .director(director)
                .releaseDate(LocalDate.of(2012, 12, 22))
                .country("country1")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(subscription);
        session.save(director);

        Serializable movieId = session.save(movie);
        session.flush();
        session.clear();

        session.remove(movie);
        session.flush();
        Movie actualResult = session.get(Movie.class, movieId);

        assertNull(actualResult);

        session.getTransaction().rollback();
    }

}