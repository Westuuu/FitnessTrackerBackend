package com.fitnesstrackerbackend.domain.trainingplan.dto;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record UserTrainingPlanDto(
        Long id,
        TrainingPlanSummaryDto plan,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        String planTitle) {
}
