package com.mentor.dmdev.dto;

import com.mentor.dmdev.enums.Genre;
import com.mentor.dmdev.enums.SubscriptionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class MovieCreateEditDto {

    @NotNull
    @Size(min = 3, max = 32)
    private String name;

    @NotNull
    private Long directorId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    @NotNull
    private String country;

    @NotNull
    private Genre genre;

    @NotNull
    private Long subscriptionId;

    private SubscriptionTypes subscriptionTypes;
}