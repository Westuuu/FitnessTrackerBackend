package com.fitnesstrackerbackend.domain.trainingplan.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record WorkoutDayDto(
        @NotNull @Size(max = 255) String name,
        @NotNull Integer dayOfWeek,
        String notes,
        List<WorkoutExerciseDto> exercises) {
}
