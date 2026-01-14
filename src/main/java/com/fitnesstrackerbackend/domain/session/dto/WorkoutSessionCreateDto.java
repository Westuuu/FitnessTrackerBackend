package com.fitnesstrackerbackend.domain.session.dto;

import java.util.List;

public record WorkoutSessionCreateDto(
        String workoutName,
        String notes,
        List<ExerciseSessionDto> exercises) {
    public record ExerciseSessionDto(
            String exerciseName,
            List<SetSessionDto> sets) {
    }

    public record SetSessionDto(
            Integer setNumber,
            java.math.BigDecimal weight,
            Integer reps) {
    }
}
