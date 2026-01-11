package com.fitnesstrackerbackend.domain.user.dto;

import java.time.LocalDateTime;

public record TrainerAssigmentResponseDto(
        Long trainerId,
        Long traineeId,
        LocalDateTime assignedAt
) {
}
