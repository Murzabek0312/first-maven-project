package com.mentor.dmdev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class UserCreateEditDto {

    @NotNull
    @Size(min = 3, max = 64)
    private String username;

    @NotNull
    @Size(min = 3, max = 64)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 64)
    private String secondName;

    @NotNull
    @Email(message = "Некорректный формат электронной почты")
    private String email;

    private MultipartFile image;
}