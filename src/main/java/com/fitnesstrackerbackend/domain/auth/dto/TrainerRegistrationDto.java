package com.fitnesstrackerbackend.domain.auth.dto;

import com.fitnesstrackerbackend.domain.user.model.Sex;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TrainerRegistrationDto(
        @NotBlank String firstName,

        @NotBlank String lastName,

        String middleName,

        @NotBlank @Email String email,

        @NotBlank @Size(min = 6) String password,

        @NotNull @Past LocalDate dateOfBirth,

        @NotNull Sex sex) {
}
