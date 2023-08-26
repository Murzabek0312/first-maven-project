package com.mentor.dmdev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class LoginDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}