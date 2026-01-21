package com.fitnesstrackerbackend.domain.session.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExerciseProgressDto(LocalDate date, BigDecimal maxWeight) {
}
