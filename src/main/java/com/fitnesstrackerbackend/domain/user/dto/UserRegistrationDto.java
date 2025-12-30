package com.fitnesstrackerbackend.domain.user.dto;

import com.fitnesstrackerbackend.domain.user.model.Sex;
import com.fitnesstrackerbackend.domain.user.model.UserType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class UserRegistrationDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private UserType userType;

    @NotNull
    private Integer gymId;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    private Sex sex;
}
