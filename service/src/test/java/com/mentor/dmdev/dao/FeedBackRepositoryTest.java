package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseTest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FeedBackRepositoryTest extends BaseTest {

    private static FeedBackRepository feedBackRepository;
    private static Long feedbackId;

    @BeforeEach
    void prepare() {
        feedBackRepository = new FeedBackRepository(session);
        session.beginTransaction();
        FeedBack feedback = getFeedback("comment", Rating.EXCELLENT, "email");
        feedbackId = (long) session.save(feedback);
    }

    @Test
    void shouldGetFeedback() {
        Optional<FeedBack> actualResult = feedBackRepository.findById(feedbackId);
        assertTrue(actualResult.isPresent());
        assertEquals("comment", actualResult.get().getComment());
        assertEquals(Rating.EXCELLENT, actualResult.get().getRating());
    }

    @Test
    void shouldUpdateFeedback() {
        FeedBack feedBack = feedBackRepository.findById(feedbackId).get();
        feedBack.setComment("new Comment");
        feedBack.setRating(Rating.BAD);
        feedBackRepository.update(feedBack);
        session.flush();
        session.clear();
        Optional<FeedBack> actualResult = feedBackRepository.findById(feedbackId);
        assertTrue(actualResult.isPresent());
        assertEquals("new Comment", actualResult.get().getComment());
        assertEquals(Rating.BAD, actualResult.get().getRating());
    }

    @Test
    void shouldDeleteFeedback() {
        FeedBack feedBack = feedBackRepository.findById(feedbackId).get();
        feedBackRepository.delete(feedBack);
        Optional<FeedBack> actualResult = feedBackRepository.findById(feedbackId);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllFeedback() {
        FeedBack feedback1 = getFeedback("comment2", Rating.NORM, "email2");
        FeedBack feedback2 = getFeedback("comment3", Rating.BAD, "email3");

        feedBackRepository.save(feedback1);
        feedBackRepository.save(feedback2);
        session.flush();
        session.clear();

        List<FeedBack> actualResult = feedBackRepository.findAll();

        assertEquals(3, actualResult.size());
    }

    private static FeedBack getFeedback(String comment, Rating rating, String userEmail) {
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
                .email(userEmail)
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
                .comment(comment)
                .rating(rating)
                .user(user)
                .build();

        return feedBack;
    }
}