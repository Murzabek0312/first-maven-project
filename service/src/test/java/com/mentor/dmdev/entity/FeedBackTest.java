package com.mentor.dmdev.entity;

import com.mentor.dmdev.enums.Genre;
import com.mentor.dmdev.enums.Rating;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class FeedBackTest {
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void prepare() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @Test
    void shouldSaveFeedback() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        var actor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(director);
        session.save(actor);

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

        var user = User.builder()
                .username("username")
                .firstName("firstName")
                .secondName("secondName")
                .password("password")
                .email("email")
                .subscription(subscription)
                .build();

        session.save(user);

        var moviesActor = MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();

        session.save(moviesActor);

        var feedBack = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        var feedBackId = session.save(feedBack);

        assertNotNull(feedBackId);

        session.getTransaction().rollback();
    }

    @Test
    void shouldGetFeedback() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        var actor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(director);
        session.save(actor);

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

        var user = User.builder()
                .username("username")
                .firstName("firstName")
                .secondName("secondName")
                .password("password")
                .email("email")
                .subscription(subscription)
                .build();

        session.save(user);

        var moviesActor = MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();

        session.save(moviesActor);

        var feedBack = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        var feedBackId = session.save(feedBack);
        session.flush();
        session.clear();

        FeedBack actualResult = session.get(FeedBack.class, feedBackId);

        assertEquals(movie.getId(), actualResult.getMovie().getId());
        assertEquals("comment", actualResult.getComment());
        assertEquals(Rating.EXCELLENT, actualResult.getRating());
        assertEquals(user, actualResult.getUser());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateFeedback() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        var actor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(director);
        session.save(actor);

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

        var user = User.builder()
                .username("username")
                .firstName("firstName")
                .secondName("secondName")
                .password("password")
                .email("email")
                .subscription(subscription)
                .build();

        session.save(user);

        var moviesActor = MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();

        session.save(moviesActor);

        var feedBack = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        var feedBackId = session.save(feedBack);
        session.flush();
        session.clear();

        FeedBack currentEntity = session.get(FeedBack.class, feedBackId);

        currentEntity.setComment("new Comment");
        currentEntity.setRating(Rating.BAD);

        session.update(currentEntity);
        session.flush();
        session.clear();

        FeedBack actualResult = session.get(FeedBack.class, feedBackId);

        assertEquals(movie.getId(), actualResult.getMovie().getId());
        assertEquals("new Comment", actualResult.getComment());
        assertEquals(Rating.BAD, actualResult.getRating());
        assertEquals(user, actualResult.getUser());

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteFeedback() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();

        var actor = Actor.builder()
                .firstname("actorName")
                .secondname("astorSecondName")
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
        session.save(director);
        session.save(actor);

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

        var user = User.builder()
                .username("username")
                .firstName("firstName")
                .secondName("secondName")
                .password("password")
                .email("email")
                .subscription(subscription)
                .build();

        session.save(user);

        var moviesActor = MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();

        session.save(moviesActor);

        var feedBack = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        var feedBackId = session.save(feedBack);
        session.flush();
        session.clear();

        session.remove(feedBack);
        session.flush();

        FeedBack actualResult = session.get(FeedBack.class, feedBackId);

        assertNull(actualResult);
        session.getTransaction().rollback();
    }

    @AfterAll
    static void clear() {
        sessionFactory.close();
    }
}