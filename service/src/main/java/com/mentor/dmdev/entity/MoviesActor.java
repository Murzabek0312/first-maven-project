package com.mentor.dmdev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie_actor")
public class MoviesActor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Movie movie;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Actor actor;

    public void setMovie(Movie movie) {
        this.movie = movie;
        this.movie.getMoviesActors().add(this);
    }

    public void setActor(Actor actor) {
        this.actor = actor;
        this.actor.getMoviesActors().add(this);
    }
}