package com.mentor.dmdev.dto.filters;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class ActorFilter {

    @NotNull
    String firstname;

    @NotNull
    String secondname;
}