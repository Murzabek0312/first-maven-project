package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.MoviesActor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class MovieActorRepositoryTest extends BaseIT {

    private static final Long MOVIE_ACTOR_ID = 1L;

    private final MovieActorRepository movieActorRepository;
    private final MovieRepository movieRepository;

    @Test
    void shouldGetMovieActor() {
        Optional<MoviesActor> actualResult = movieActorRepository.findById(MOVIE_ACTOR_ID);
        assertTrue(actualResult.isPresent());
        assertEquals("Petr", actualResult.get().getActor().getFirstname());
        assertEquals("actorSecondName1", actualResult.get().getActor().getSecondname());
    }

    @Test
    void shouldUpdateMovieActor() {
        MoviesActor moviesActor = movieActorRepository.findById(MOVIE_ACTOR_ID).get();
        Movie movie = movieRepository.findById(2L).get();
        moviesActor.setMovie(movie);
        movieActorRepository.update(moviesActor);
        session.flush();
        session.clear();

        Optional<MoviesActor> actualResult = movieActorRepository.findById(MOVIE_ACTOR_ID);

        assertTrue(actualResult.isPresent());
        assertEquals("movieName", actualResult.get().getMovie().getName());
        assertEquals("USA", actualResult.get().getMovie().getCountry());
    }

    @Test
    void shouldDeleteMovieActor() {
        MoviesActor moviesActor = movieActorRepository.findById(MOVIE_ACTOR_ID).get();
        movieActorRepository.delete(moviesActor);

        Optional<MoviesActor> actualResult = movieActorRepository.findById(MOVIE_ACTOR_ID);
        assertFalse(actualResult.isPresent());
    }

    @Test
    void shouldReturnAllMovieActors() {
        List<MoviesActor> actualResult = movieActorRepository.findAll();
        assertEquals(2, actualResult.size());
    }
}