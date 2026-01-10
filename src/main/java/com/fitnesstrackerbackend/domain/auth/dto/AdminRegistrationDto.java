package com.fitnesstrackerbackend.domain.auth.dto;

import com.fitnesstrackerbackend.domain.user.model.Sex;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AdminRegistrationDto(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        String middleName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 6)
        String password,

        @NotNull
        Long gymId,

        @NotNull
        @Past
        LocalDate dateOfBirth,

        @NotNull
        Sex sex
) {
}
