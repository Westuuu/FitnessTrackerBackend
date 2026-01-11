package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.domain.trainingplan.dto.TrainingPlanDto;
import com.fitnesstrackerbackend.domain.trainingplan.dto.TrainingPlanSummaryDto;
import com.fitnesstrackerbackend.domain.trainingplan.model.TrainingPlanEntity;
import org.springframework.stereotype.Component;

@Component
public class TrainingPlanMapper {
    protected TrainingPlanSummaryDto mapToSummaryDto(TrainingPlanEntity trainingPlan) {
        return TrainingPlanSummaryDto.builder()
                .id(trainingPlan.getId())
                .name(trainingPlan.getName())
                .description(trainingPlan.getDescription())
                .difficultyLevel(trainingPlan.getDifficultyLevel())
                .durationWeeks(trainingPlan.getDurationWeeks())
                .visibilityType(trainingPlan.getVisibilityType())
                .createdAt(trainingPlan.getCreatedAt())
                .updatedAt(trainingPlan.getUpdatedAt())
                .build();
    }
}
