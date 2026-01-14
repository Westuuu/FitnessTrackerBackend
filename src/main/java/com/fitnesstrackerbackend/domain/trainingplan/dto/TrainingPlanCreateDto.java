package com.fitnesstrackerbackend.domain.trainingplan.dto;

import com.fitnesstrackerbackend.domain.trainingplan.enums.DifficultyLevel;
import com.fitnesstrackerbackend.domain.trainingplan.enums.VisibilityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TrainingPlanCreateDto(
                @NotNull @Size(max = 255) String name,
                String description,
                DifficultyLevel difficultyLevel,
                @NotNull Integer durationWeeks,
                @NotNull VisibilityType visibilityType,
                java.util.List<WorkoutDayDto> workoutDays) {
}
