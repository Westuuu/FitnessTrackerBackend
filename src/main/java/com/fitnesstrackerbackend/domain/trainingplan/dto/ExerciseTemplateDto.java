package com.fitnesstrackerbackend.domain.trainingplan.dto;

import lombok.Builder;

@Builder
public record ExerciseTemplateDto(
        Long id,
        String name,
        String description,
        String muscleGroup,
        String equipmentNeeded,
        String instructions
) {
}
