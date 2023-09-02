package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.MovieCreateEditDto;
import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.entity.FeedBack;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.MoviesActor;
import com.mentor.dmdev.entity.Subscription;
import com.mentor.dmdev.enums.Genre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class MovieMapperTest {

    private final MovieMapper mapper = new MovieMapperImpl();

    @Test
    void shouldMapToMovieReadDtoFromMovie() {
        // Given:
        var feedbackId1 = 1L;
        var feedbackId2 = 2L;
        var movieActorId1 = 3L;
        var movieActorId2 = 4L;
        var directorId = 111L;
        var subscriptionId = 444L;
        var movieId = 434L;
        var movieName = "movieName";
        var releaseDate = LocalDate.now();
        var country = "country";
        var genre = Genre.ACTION;

        var movie = mock(Movie.class);
        var feedBack1 = mock(FeedBack.class);
        var feedBack2 = mock(FeedBack.class);
        var moviesActor1 = mock(MoviesActor.class);
        var moviesActor2 = mock(MoviesActor.class);
        var actor1 = mock(Actor.class);
        var actor2 = mock(Actor.class);
        var director = mock(Actor.class);
        var subscription = mock(Subscription.class);

        doReturn(List.of(feedBack1, feedBack2)).when(movie).getFeedbacks();
        doReturn(feedbackId1).when(feedBack1).getId();
        doReturn(feedbackId2).when(feedBack2).getId();
        doReturn(actor1).when(moviesActor1).getActor();
        doReturn(actor2).when(moviesActor2).getActor();
        doReturn(List.of(moviesActor1, moviesActor2)).when(movie).getMoviesActors();
        doReturn(movieActorId1).when(actor1).getId();
        doReturn(movieActorId2).when(actor2).getId();
        doReturn(director).when(movie).getDirector();
        doReturn(directorId).when(director).getId();
        doReturn(subscription).when(movie).getSubscription();
        doReturn(subscriptionId).when(subscription).getId();
        doReturn(movieId).when(movie).getId();
        doReturn(movieName).when(movie).getName();
        doReturn(releaseDate).when(movie).getReleaseDate();
        doReturn(country).when(movie).getCountry();
        doReturn(genre).when(movie).getGenre();

        // When:
        var result = mapper.map(movie);

        // Then:
        assertEquals(movieId, result.getId());
        assertEquals(movieName, result.getName());
        assertEquals(directorId, result.getDirectorId());
        assertEquals(releaseDate, result.getReleaseDate());
        assertEquals(country, result.getCountry());
        assertEquals(genre, result.getGenre());
        assertEquals(subscriptionId, result.getSubscriptionId());
        assertEquals(2, result.getFeedbackIds().size());
        assertEquals(2, result.getActorIds().size());
    }

    @Test
    void shouldMapToListMovieReadDtoFromListMovie() {
        // Given:
        var movie1 = mock(Movie.class);
        var movie2 = mock(Movie.class);

        // When:
        var result = mapper.map(List.of(movie1, movie2));

        // Then:
        assertEquals(2, result.size());
    }

    @Test
    void shouldMapMovieFromMovieCreateEditDto() {
        // Given:
        var directorId = 123L;
        var subscriptionId = 333L;
        var name = "name";
        var country = "country";
        var genre = Genre.ACTION;

        var releaseDate = LocalDate.now();

        var movieCreateEditDto = mock(MovieCreateEditDto.class);

        doReturn(directorId).when(movieCreateEditDto).getDirectorId();
        doReturn(subscriptionId).when(movieCreateEditDto).getSubscriptionId();
        doReturn(name).when(movieCreateEditDto).getName();
        doReturn(releaseDate).when(movieCreateEditDto).getReleaseDate();
        doReturn(country).when(movieCreateEditDto).getCountry();
        doReturn(genre).when(movieCreateEditDto).getGenre();

        // When:
        var result = mapper.map(movieCreateEditDto);

        // Then:
        assertEquals(directorId, result.getDirector().getId());
        assertEquals(subscriptionId, result.getSubscription().getId());
        assertEquals(name, result.getName());
        assertEquals(country, result.getCountry());
        assertEquals(genre, result.getGenre());
    }

    @Test
    void shouldMapMovieFromMovieCreateEditDtoAndMovie() {
        // Given:
        var directorId = 123L;
        var subscriptionId = 323L;
        var name = "name";
        var releaseDate = LocalDate.now();
        var country = "country";
        var genre = Genre.ACTION;
        var movieId = 444L;

        var movieCreateEditDto = mock(MovieCreateEditDto.class);
        var movie = mock(Movie.class);
        var moviesActor1 = mock(MoviesActor.class);
        var moviesActor2 = mock(MoviesActor.class);
        var feedBack1 = mock(FeedBack.class);
        var feedBack2 = mock(FeedBack.class);

        doReturn(directorId).when(movieCreateEditDto).getDirectorId();
        doReturn(subscriptionId).when(movieCreateEditDto).getSubscriptionId();
        doReturn(name).when(movieCreateEditDto).getName();
        doReturn(releaseDate).when(movieCreateEditDto).getReleaseDate();
        doReturn(country).when(movieCreateEditDto).getCountry();
        doReturn(genre).when(movieCreateEditDto).getGenre();
        doReturn(movieId).when(movie).getId();
        doReturn(List.of(moviesActor1, moviesActor2)).when(movie).getMoviesActors();
        doReturn(List.of(feedBack1, feedBack2)).when(movie).getFeedbacks();

        // When:
        var result = mapper.map(movieCreateEditDto, movie);

        // Then:
        assertEquals(directorId, result.getDirector().getId());
        assertEquals(subscriptionId, result.getSubscription().getId());
        assertEquals(name, result.getName());
        assertEquals(releaseDate, result.getReleaseDate());
        assertEquals(country, result.getCountry());
        assertEquals(genre, result.getGenre());
        assertEquals(movieId, result.getId());
    }
}