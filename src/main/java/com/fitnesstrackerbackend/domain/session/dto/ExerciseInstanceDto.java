package com.fitnesstrackerbackend.domain.session.dto;

import java.util.List;

public record ExerciseInstanceDto(
        Long id,
        Long exerciseTemplateId,
        String exerciseName,
        Integer orderInWorkout,
        Boolean completed,
        List<ExerciseSetDto> sets) {
}
