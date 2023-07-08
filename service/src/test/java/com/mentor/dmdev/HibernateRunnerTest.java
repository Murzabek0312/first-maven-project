package com.mentor.dmdev;

import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.entity.FeedBack;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.MoviesActor;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.entity.User;
import com.mentor.dmdev.enums.Genre;
import com.mentor.dmdev.enums.Rating;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HibernateRunnerTest {

    @Test
    void shouldSaveAndGetActor() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var actor1 = Actor.builder()
                    .firstname("actorName1")
                    .secondname("astorSecondName1")
                    .birthDate(LocalDate.of(2012, 5, 13))
                    .biography("actorBiography1")
                    .build();

            var actor2 = Actor.builder()
                    .firstname("actorName2")
                    .secondname("astorSecondName2")
                    .birthDate(LocalDate.of(2012, 11, 23))
                    .biography("actorBiography2")
                    .build();

            var actorId = session.save(actor1);
            var actor2Id = session.save(actor2);
            session.flush();

            var actualResult = session.get(Actor.class, actorId);

            assertNotNull(actorId);
            assertNotNull(actor2Id);
            assertEquals("actorName1", actualResult.getFirstname());
            assertEquals("astorSecondName1", actualResult.getSecondname());
            assertEquals(LocalDate.of(2012, 5, 13), actualResult.getBirthDate());
            assertEquals("actorBiography1", actualResult.getBiography());

            session.remove(actualResult);
            session.remove(session.get(Actor.class, actor2Id));

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldSaveAndGetSubscription() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var subscription1 = Subscription.builder()
                    .type(SubscriptionTypes.PREMIUM)
                    .status(SubscriptionStatus.ACTIVE)
                    .build();

            var subscription2 = Subscription.builder()
                    .type(SubscriptionTypes.ULTRA)
                    .status(SubscriptionStatus.EXPIRED)
                    .build();

            var subscription1Id = session.save(subscription1);
            var subscription2Id = session.save(subscription2);

            session.flush();

            assertNotNull(subscription1Id);
            assertNotNull(subscription2Id);

            var actualResult = session.get(Subscription.class, subscription1Id);
            assertEquals(SubscriptionTypes.PREMIUM, actualResult.getType());
            assertEquals(SubscriptionStatus.ACTIVE, actualResult.getStatus());

            session.remove(actualResult);
            session.remove(session.get(Subscription.class, subscription2Id));

            session.getTransaction().commit();

        }
    }

    @Test
    void shouldSaveAndGetUser() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var subscription = Subscription.builder()
                    .type(SubscriptionTypes.PREMIUM)
                    .status(SubscriptionStatus.ACTIVE)
                    .build();
            var subscriptionId = session.save(subscription);

            var user1 = User.builder()
                    .username("username1")
                    .firstName("firstName1")
                    .secondName("secondName1")
                    .password("password1")
                    .email("email1")
                    .subscriptionId((long) subscriptionId)
                    .build();

            var user2 = User.builder()
                    .username("username2")
                    .firstName("firstName2")
                    .secondName("secondName2")
                    .password("password2")
                    .email("email2")
                    .subscriptionId((long) subscriptionId)
                    .build();

            var user1Id = session.save(user1);
            var user2Id = session.save(user2);

            session.flush();

            assertNotNull(user1Id);
            assertNotNull(user2Id);

            var actualResult = session.get(User.class, user1Id);

            assertEquals("username1", actualResult.getUsername());
            assertEquals("firstName1", actualResult.getFirstName());
            assertEquals("secondName1", actualResult.getSecondName());
            assertEquals("password1", actualResult.getPassword());
            assertEquals("email1", actualResult.getEmail());
            assertEquals((long) subscriptionId, actualResult.getSubscriptionId());

            session.remove(actualResult);
            session.remove(session.get(User.class, user2Id));

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldSaveAndGetMovie() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var director = Actor.builder()
                    .firstname("directorName")
                    .secondname("directorSecondName")
                    .birthDate(LocalDate.of(2000, 12, 12))
                    .biography("directorBiography")
                    .build();
            var directorId = session.save(director);

            var subscription = Subscription.builder()
                    .type(SubscriptionTypes.PREMIUM)
                    .status(SubscriptionStatus.ACTIVE)
                    .build();
            var subscriptionId = session.save(subscription);

            var movie1 = Movie.builder()
                    .name("movieName1")
                    .director((long) directorId)
                    .releaseDate(LocalDate.of(2012, 12, 22))
                    .country("country1")
                    .genre(Genre.TRILLER)
                    .subscriptionId((long) subscriptionId)
                    .build();

            var movie2 = Movie.builder()
                    .name("movieName2")
                    .director((long) directorId)
                    .releaseDate(LocalDate.of(2022, 2, 12))
                    .country("country2")
                    .genre(Genre.ACTION)
                    .subscriptionId((long) subscriptionId)
                    .build();

            var movie1Id = session.save(movie1);
            var movie2Id = session.save(movie2);

            assertNotNull(movie1Id);
            assertNotNull(movie2Id);

            var actualResult = session.get(Movie.class, movie1Id);

            assertEquals("movieName1", actualResult.getName());
            assertEquals((long) directorId, actualResult.getDirector());
            assertEquals(LocalDate.of(2012, 12, 22), actualResult.getReleaseDate());
            assertEquals("country1", actualResult.getCountry());
            assertEquals(Genre.TRILLER, actualResult.getGenre());
            assertEquals((long) subscriptionId, actualResult.getSubscriptionId());

            session.remove(actualResult);
            session.remove(session.get(Movie.class, movie2Id));
        }
    }

    @Test
    void shouldSaveAndGetMoviesActor() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            var actor = Actor.builder()
                    .firstname("actorName")
                    .secondname("astorSecondName")
                    .birthDate(LocalDate.of(2012, 11, 23))
                    .biography("actorBiography")
                    .build();
            var actorId = session.save(actor);

            var director = Actor.builder()
                    .firstname("directorName")
                    .secondname("directorSecondName")
                    .birthDate(LocalDate.of(2000, 12, 12))
                    .biography("directorBiography")
                    .build();

            var directorId = session.save(director);

            var subscription = Subscription.builder()
                    .type(SubscriptionTypes.PREMIUM)
                    .status(SubscriptionStatus.ACTIVE)
                    .build();
            var subscriptionId = session.save(subscription);

            var movie = Movie.builder()
                    .name("movieName")
                    .director((long) directorId)
                    .releaseDate(LocalDate.of(2022, 2, 12))
                    .country("country")
                    .genre(Genre.TRILLER)
                    .subscriptionId((long) subscriptionId)
                    .build();

            var movieId = session.save(movie);

            var moviesActor = MoviesActor.builder()
                    .actorId((long) actorId)
                    .movieId((long) movieId)
                    .build();

            var moviesActorId = session.save(moviesActor);

            assertNotNull(moviesActorId);

            var actualResult = session.get(MoviesActor.class, moviesActorId);

            assertEquals(actorId, actualResult.getActorId());
            assertEquals(movieId, actualResult.getMovieId());

            session.remove(actualResult);

            session.getTransaction().commit();
        }
    }

    @Test
    void shouldSaveAndGetFeedback() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
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


            var directorId = session.save(director);
            var actorId = session.save(actor);

            var subscription = Subscription.builder()
                    .type(SubscriptionTypes.PREMIUM)
                    .status(SubscriptionStatus.ACTIVE)
                    .build();
            var subscriptionId = session.save(subscription);

            var movie = Movie.builder()
                    .name("movieName")
                    .director((long) directorId)
                    .releaseDate(LocalDate.of(2022, 2, 12))
                    .country("country")
                    .genre(Genre.TRILLER)
                    .subscriptionId((long) subscriptionId)
                    .build();

            var movieId = session.save(movie);

            var user = User.builder()
                    .username("username")
                    .firstName("firstName")
                    .secondName("secondName")
                    .password("password")
                    .email("email")
                    .subscriptionId((long) subscriptionId)
                    .build();

            var userId = session.save(user);

            var moviesActor = MoviesActor.builder()
                    .actorId((long) actorId)
                    .movieId((long) movieId)
                    .build();

            var moviesActorId = session.save(moviesActor);

            var feedBack = FeedBack.builder()
                    .movieId((long) movieId)
                    .userId((long) userId)
                    .comment("comment")
                    .rating(Rating.EXCELLENT)
                    .build();

            var feedBackId = session.save(feedBack);

            session.flush();

            assertNotNull(feedBackId);

            var actualResult = session.get(FeedBack.class, feedBackId);

            assertNotNull(actualResult);
            assertEquals(userId, actualResult.getUserId());
            assertEquals(movieId, actualResult.getMovieId());
            assertEquals(Rating.EXCELLENT, actualResult.getRating());
            assertEquals("comment", actualResult.getComment());

            session.remove(session.get(MoviesActor.class, moviesActorId));
            session.remove(session.get(FeedBack.class, feedBackId));
            session.remove(session.get(Movie.class, movieId));
            session.remove(session.get(Actor.class, actorId));
            session.remove(session.get(Actor.class, directorId));
            session.remove(session.get(User.class, userId));
            session.remove(session.get(Subscription.class, subscriptionId));

            session.getTransaction().commit();
        }
    }
}