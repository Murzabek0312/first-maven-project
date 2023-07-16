package com.mentor.dmdev.entity;

import com.mentor.dmdev.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(exclude = {"moviesActors,feedbacks"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Actor director;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Subscription subscription;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "movie")
    private List<MoviesActor> moviesActors = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedBack> feedbacks = new ArrayList<>();

    public void addFeedback(FeedBack feedBack) {
        feedbacks.add(feedBack);
        feedBack.setMovie(this);
    }
}