package com.fitnesstrackerbackend.domain.goal.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalCreateDto(
        @NotNull Long exerciseTemplateId,
        @NotNull @Size(max = 255) String title,
        String description,
        @NotNull BigDecimal targetValue,
        @NotNull LocalDate targetDate) {
}
