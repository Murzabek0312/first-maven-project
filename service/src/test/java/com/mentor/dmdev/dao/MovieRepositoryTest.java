package com.mentor.dmdev.dao;

import com.mentor.dmdev.BaseIT;
import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.entity.Movie;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieRepositoryTest extends BaseIT {

    private static MovieRepository movieRepository;
    private static Long movieId;

    @BeforeEach
    void prepare() {
        movieRepository = context.getBean(MovieRepository.class);
        Movie movie = createMovie("The Hateful Eight", "USA", Genre.ACTION);
        movieId = (long) session.save(movie);
    }

    @Test
    void shouldGetMovie() {
        Optional<Movie> actualResult = movieRepository.findById(movieId);
        assertTrue(actualResult.isPresent());
        assertEquals("The Hateful Eight", actualResult.get().getName());
        assertEquals("USA", actualResult.get().getCountry());
        assertEquals(Genre.ACTION, actualResult.get().getGenre());
    }

    @Test
    void shouldUpdateMovie() {
        Movie movie = movieRepository.findById(movieId).get();
        movie.setName("The Pursuit of Happyness");
        movie.setGenre(Genre.DRAMA);
        movieRepository.update(movie);
        session.flush();
        session.clear();

        Optional<Movie> actualResult = movieRepository.findById(movieId);
        assertTrue(actualResult.isPresent());
        assertEquals("The Pursuit of Happyness", actualResult.get().getName());
        assertEquals(Genre.DRAMA, actualResult.get().getGenre());
    }

    @Test
    void shouldReturnAllMovie() {
        Movie movie1 = createMovie("The Pursuit of Happyness", "USA", Genre.DRAMA);
        Movie movie2 = createMovie("Gone with the Wind ", "USA", Genre.DRAMA);
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        List<Movie> actualResult = movieRepository.findAll();
        assertEquals(3, actualResult.size());
    }

    private static Movie createMovie(String movieName, String country, Genre genre) {
        Subscription subscription = Subscription.builder()
                .type(SubscriptionTypes.PREMIUM)
                .status(SubscriptionStatus.ACTIVE)
                .build();
        session.save(subscription);

        Actor director = Actor.builder()
                .firstname("Quentin")
                .secondname("Tarantino")
                .birthDate(LocalDate.of(1963, 3, 27))
                .biography("Cool boy")
                .build();
        session.save(director);

        return Movie.builder()
                .name(movieName)
                .director(director)
                .country(country)
                .genre(genre)
                .subscription(subscription)
                .releaseDate(LocalDate.of(2016, 1, 1))
                .build();
    }
}