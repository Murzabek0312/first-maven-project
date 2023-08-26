package com.mentor.dmdev.dto;

import com.mentor.dmdev.entity.Movie;
import com.mentor.dmdev.entity.User;
import com.mentor.dmdev.enums.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackReadDto {

    private Long id;
    private Movie movie;
    private User user;
    private String comment;
    private Rating rating;
}