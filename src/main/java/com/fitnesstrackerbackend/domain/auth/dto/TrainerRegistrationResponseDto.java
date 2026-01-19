package com.fitnesstrackerbackend.domain.auth.dto;

import com.fitnesstrackerbackend.domain.user.model.UserType;
import lombok.Builder;

@Builder
public record TrainerRegistrationResponseDto(
        Long userId,
        String firstName,
        String lastName,
        String email,
        UserType userType) {
}
