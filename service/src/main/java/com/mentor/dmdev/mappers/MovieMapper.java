package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.MovieCreateEditDto;
import com.mentor.dmdev.dto.MovieReadDto;
import com.mentor.dmdev.entity.FeedBack;
import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.MoviesActor;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface MovieMapper {

    default List<Long> getAllActorIds(List<MoviesActor> moviesActors) {
        return moviesActors.stream()
                .map(moviesActor -> moviesActor.getActor().getId())
                .collect(Collectors.toList());
    }

    default List<Long> getAllFeedbackIds(List<FeedBack> feedbacks) {
        return feedbacks.stream()
                .map(FeedBack::getId)
                .collect(Collectors.toList());
    }

    @Mapping(target = "feedbackIds", source = "source.feedbacks")
    @Mapping(target = "actorIds", source = "source.moviesActors")
    @Mapping(target = "subscriptionId", source = "source.subscription.id")
    @Mapping(target = "directorId", source = "source.director.id")
    MovieReadDto map(Movie source);

    List<MovieReadDto> map(List<Movie> source);

    @Mapping(target = "moviesActors", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "feedbacks", ignore = true)
    @Mapping(target = "director.id", source = "directorId")
    @Mapping(target = "subscription.id", source = "subscriptionId")
    Movie map(MovieCreateEditDto source);

    @Mapping(target = "id", source = "movie.id")
    @Mapping(target = "moviesActors", source = "movie.moviesActors")
    @Mapping(target = "feedbacks", source = "movie.feedbacks")
    @Mapping(target = "name", source = "source.name")
    @Mapping(target = "director.id", source = "source.directorId")
    @Mapping(target = "releaseDate", source = "source.releaseDate")
    @Mapping(target = "country", source = "source.country")
    @Mapping(target = "genre", source = "source.genre")
    @Mapping(target = "subscription.id", source = "source.subscriptionId")
    Movie map(MovieCreateEditDto source, Movie movie);
}