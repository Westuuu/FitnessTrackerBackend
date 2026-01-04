package com.fitnesstrackerbackend.domain.gym.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GymCreationRequestDto(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 255)
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Country is required")
        String country,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Post code is required")
        @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "Post code must be in XX-XXX format")
        String postCode,

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "Street number is required")
        String streetNumber,

        String apartmentNumber
) {
}
