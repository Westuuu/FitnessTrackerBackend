package com.fitnesstrackerbackend.domain.session.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record WorkoutSessionDto(
        Long id,
        Long userTrainingPlanId,
        String planTitle,
        LocalDate sessionDate,
        LocalTime startTime,
        LocalTime endTime,
        String notes,
        Boolean completed,
        List<ExerciseInstanceDto> exercises) {
}
