package com.mentor.dmdev.dto.filters;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {

    String firstName;
    String secondName;
}