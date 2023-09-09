package com.mentor.dmdev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorReadDto {

    private Long id;
    private String firstname;
    private String secondname;
    private LocalDate birthDate;
    private String biography;
}