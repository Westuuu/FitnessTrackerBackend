package com.fitnesstrackerbackend.domain.auth.dto;

import com.fitnesstrackerbackend.domain.user.model.UserType;
import lombok.Builder;

@Builder
public record AdminRegistrationResponseDto(
        Long userId,

        String email,

        String firstName,

        String lastName,

        UserType userType,

        Long gymId
) {
}
