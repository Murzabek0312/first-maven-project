package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.MoviesActor;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.Genre;
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

class MovieActorRepositoryTest extends BaseIT {

    private static MovieActorRepository movieActorRepository;
    private static Long movieActorId;

    @BeforeEach
    void prepare() {
        movieActorRepository = context.getBean(MovieActorRepository.class);
        MoviesActor movieActor = createMovieActor("Bruce", "Willis");
        movieActorId = (long) session.save(movieActor);
    }

    @Test
    void shouldGetMovieActor() {
        Optional<MoviesActor> actualResult = movieActorRepository.findById(movieActorId);
        assertTrue(actualResult.isPresent());
        assertEquals("Bruce", actualResult.get().getActor().getFirstname());
        assertEquals("Willis", actualResult.get().getActor().getSecondname());
    }

    @Test
    void shouldUpdateMovieActor() {
        MoviesActor moviesActor = movieActorRepository.findById(movieActorId).get();
        Subscription subscription = Subscription.builder()
                .type(SubscriptionTypes.STANDART)
                .status(SubscriptionStatus.ACTIVE)
                .build();
        session.save(subscription);

        var actor = Actor.builder()
                .firstname("James")
                .secondname("Bond")
                .birthDate(LocalDate.of(2012, 5, 13))
                .biography("Agent007")
                .build();
        session.save(actor);

        var movie = Movie.builder()
                .name("new Movie")
                .director(actor)
                .releaseDate(LocalDate.of(2022, 2, 12))
                .country("USA")
                .genre(Genre.TRILLER)
                .subscription(subscription)
                .build();

        session.save(movie);
        moviesActor.setMovie(movie);
        movieActorRepository.update(moviesActor);
        session.flush();
        session.clear();

        Optional<MoviesActor> actualResult = movieActorRepository.findById(movieActorId);

        assertTrue(actualResult.isPresent());
        assertEquals("new Movie", actualResult.get().getMovie().getName());
        assertEquals("USA", actualResult.get().getMovie().getCountry());
    }

    @Test
    void shouldDeleteMovieActor() {
        MoviesActor moviesActor = movieActorRepository.findById(movieActorId).get();
        movieActorRepository.delete(moviesActor);

        Optional<MoviesActor> actualResult = movieActorRepository.findById(movieActorId);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllMovieActors() {
        MoviesActor movieActor1 = createMovieActor("Will", "Smith");
        MoviesActor movieActor2 = createMovieActor("Jeki", "Chan");
        movieActorRepository.save(movieActor1);
        movieActorRepository.save(movieActor2);

        List<MoviesActor> actualResult = movieActorRepository.findAll();
        assertEquals(3, actualResult.size());
    }

    private static MoviesActor createMovieActor(String actorName, String actorSecondname) {
        var director = Actor.builder()
                .firstname("directorName")
                .secondname("directorSecondName")
                .birthDate(LocalDate.of(2000, 12, 12))
                .biography("directorBiography")
                .build();
        session.save(director);
        var actor = Actor.builder()
                .firstname(actorName)
                .secondname(actorSecondname)
                .birthDate(LocalDate.of(2012, 11, 23))
                .biography("actorBiography")
                .build();
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

        return MoviesActor.builder()
                .actor(actor)
                .movie(movie)
                .build();
    }
}