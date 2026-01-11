package com.fitnesstrackerbackend.domain.trainingplan.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record ExerciseTemplateCreationDto(
        @NotBlank(message = "Exercise name is required")
        String name,
        String description,
        @NotBlank(message = "Muscle group is required")
        String muscleGroup,
        String equipmentNeeded,
        String instructions
) {
}
