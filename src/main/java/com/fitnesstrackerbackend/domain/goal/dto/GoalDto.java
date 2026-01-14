package com.fitnesstrackerbackend.domain.goal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalDto(
                Integer id,
                Long exerciseTemplateId,
                String exerciseName,
                String title,
                String description,
                BigDecimal targetValue,
                BigDecimal currentValue,
                LocalDate startDate,
                LocalDate targetDate,
                String status) {
}
