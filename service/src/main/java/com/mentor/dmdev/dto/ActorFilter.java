package com.mentor.dmdev.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ActorFilter {
    String firstname;
    String secondname;
}