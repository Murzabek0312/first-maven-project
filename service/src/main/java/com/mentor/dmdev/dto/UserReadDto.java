package com.mentor.dmdev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDto {

    private Long id;
    private String username;
    private String firstName;
    private String secondName;
    private String password;
    private String email;
    private String image;
    private SubscriptionReadDto subscription;
    private List<FeedBackReadDto> feedbacks = new ArrayList<>();
}