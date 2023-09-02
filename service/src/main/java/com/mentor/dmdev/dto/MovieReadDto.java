package com.mentor.dmdev.dto;

import com.mentor.dmdev.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieReadDto {

    private Long id;

    private String name;

    private Long directorId;

    private LocalDate releaseDate;

    private String country;

    private Genre genre;

    private Long subscriptionId;

    private List<Long> actorIds = new ArrayList<>();

    private List<Long> feedbackIds = new ArrayList<>();
}