package com.mentor.dmdev.entity;

import com.mentor.dmdev.enums.Genre;
import com.mentor.dmdev.enums.Rating;
import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import com.mentor.dmdev.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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

    @Test
    void getMovieWithFeedbackWithoutEntityGraph() {
        @Cleanup var session = sessionFactory.openSession();
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

        Movie movie2 = Movie.builder()
                .name("The Hateful Eight part2")
                .director(director)
                .country("USA")
                .genre(Genre.ACTION)
                .subscription(subscription)
                .releaseDate(LocalDate.of(2016, 1, 1))
                .build();

        Movie movie3 = Movie.builder()
                .name("The Hateful Eight part2")
                .director(director)
                .country("USA")
                .genre(Genre.ACTION)
                .subscription(subscription)
                .releaseDate(LocalDate.of(2016, 1, 1))
                .build();

        var user = User.builder()
                .username("username")
                .firstName("firstName")
                .secondName("secondName")
                .password("password")
                .email("email")
                .subscription(subscription)
                .build();

        var feedBack1 = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        var feedBack2 = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        var feedBack3 = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        movie.addFeedback(feedBack1);
        movie2.addFeedback(feedBack2);
        movie3.addFeedback(feedBack3);
        user.addFeedback(feedBack1);
        user.addFeedback(feedBack2);
        user.addFeedback(feedBack3);

        session.save(subscription);
        session.save(director);
        session.save(user);
        session.save(movie);
        session.save(movie2);
        session.save(movie3);
        session.flush();
        session.clear();

        List<Movie> movies = session.createQuery("select m from Movie m", Movie.class).list();
        movies.forEach(m -> m.getFeedbacks().size());

        session.getTransaction().rollback();
    }

    @Test
    void getMovieWithFeedbackWithEntityGraph() {
        @Cleanup var session = sessionFactory.openSession();
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

        Movie movie2 = Movie.builder()
                .name("The Hateful Eight part2")
                .director(director)
                .country("USA")
                .genre(Genre.ACTION)
                .subscription(subscription)
                .releaseDate(LocalDate.of(2016, 1, 1))
                .build();

        Movie movie3 = Movie.builder()
                .name("The Hateful Eight part2")
                .director(director)
                .country("USA")
                .genre(Genre.ACTION)
                .subscription(subscription)
                .releaseDate(LocalDate.of(2016, 1, 1))
                .build();

        var user = User.builder()
                .username("username")
                .firstName("firstName")
                .secondName("secondName")
                .password("password")
                .email("email")
                .subscription(subscription)
                .build();

        var feedBack1 = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        var feedBack2 = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        var feedBack3 = FeedBack.builder()
                .movie(movie)
                .comment("comment")
                .rating(Rating.EXCELLENT)
                .user(user)
                .build();

        movie.addFeedback(feedBack1);
        movie2.addFeedback(feedBack2);
        movie3.addFeedback(feedBack3);
        user.addFeedback(feedBack1);
        user.addFeedback(feedBack2);
        user.addFeedback(feedBack3);

        session.save(subscription);
        session.save(director);
        session.save(user);
        session.save(movie);
        session.save(movie2);
        session.save(movie3);

        session.flush();
        session.clear();

        var movieGraph = session.createEntityGraph(Movie.class);
        movieGraph.addAttributeNodes("feedbacks");

        var movies = session.createQuery("select m from Movie m", Movie.class)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), movieGraph)
                .list();

        movies.forEach(m -> m.getFeedbacks().size());

        session.getTransaction().rollback();
    }

    @AfterAll
    static void clear() {
        sessionFactory.close();
    }
}