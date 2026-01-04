package com.fitnesstrackerbackend.domain.gym.dto;

import java.time.Instant;


public record GymCreationResponseDto(
        Long id,
        String name,
        String email,
        Instant createdAt
) {
}
