package com.fitnesstrackerbackend.domain.trainingplan.dto;

import com.fitnesstrackerbackend.domain.trainingplan.enums.DifficultyLevel;
import com.fitnesstrackerbackend.domain.trainingplan.enums.VisibilityType;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TrainingPlanSummaryDto(
        Long id,
        String name,
        String description,
        DifficultyLevel difficultyLevel,
        Integer durationWeeks,
        VisibilityType visibilityType,
        Instant createdAt,
        Instant updatedAt
) {
}
