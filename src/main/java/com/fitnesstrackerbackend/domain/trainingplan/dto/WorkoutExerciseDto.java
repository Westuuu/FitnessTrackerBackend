package com.fitnesstrackerbackend.domain.trainingplan.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record WorkoutExerciseDto(
                @NotNull Long exerciseTemplateId,
                String exerciseName,
                @NotNull @Positive Integer orderInWorkout,
                @NotNull @Positive Integer plannedSets,
                @NotNull @Positive Integer plannedReps,
                java.math.BigDecimal plannedWeight,
                String notes) {
}
