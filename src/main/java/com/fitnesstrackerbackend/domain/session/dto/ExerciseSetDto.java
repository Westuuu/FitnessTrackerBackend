package com.fitnesstrackerbackend.domain.session.dto;

import java.math.BigDecimal;

public record ExerciseSetDto(
        Long id,
        Integer setNumber,
        Integer reps,
        BigDecimal weight,
        Boolean completed) {
}
