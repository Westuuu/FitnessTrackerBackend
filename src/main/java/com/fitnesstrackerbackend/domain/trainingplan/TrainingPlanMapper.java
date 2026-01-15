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

        protected TrainingPlanDto mapToDto(TrainingPlanEntity trainingPlan) {
                return TrainingPlanDto.builder()
                                .id(trainingPlan.getId())
                                .name(trainingPlan.getName())
                                .description(trainingPlan.getDescription())
                                .difficultyLevel(trainingPlan.getDifficultyLevel())
                                .durationWeeks(trainingPlan.getDurationWeeks())
                                .visibilityType(trainingPlan.getVisibilityType())
                                .createdAt(trainingPlan.getCreatedAt())
                                .updatedAt(trainingPlan.getUpdatedAt())
                                .workoutDays(trainingPlan.getWorkoutDays() != null
                                                ? trainingPlan.getWorkoutDays().stream().map(this::mapToDayDto).toList()
                                                : java.util.Collections.emptyList())
                                .build();
        }

        protected com.fitnesstrackerbackend.domain.trainingplan.dto.WorkoutDayDto mapToDayDto(
                        com.fitnesstrackerbackend.domain.trainingplan.model.WorkoutTemplateDayEntity day) {
                return new com.fitnesstrackerbackend.domain.trainingplan.dto.WorkoutDayDto(
                                day.getName(),
                                day.getDayOfWeek(),
                                day.getNotes(),
                                day.getExercises() != null
                                                ? day.getExercises().stream().map(this::mapToExerciseDto).toList()
                                                : java.util.Collections.emptyList());
        }

        protected com.fitnesstrackerbackend.domain.trainingplan.dto.WorkoutExerciseDto mapToExerciseDto(
                        com.fitnesstrackerbackend.domain.trainingplan.model.WorkoutTemplateExerciseEntity ex) {
                return new com.fitnesstrackerbackend.domain.trainingplan.dto.WorkoutExerciseDto(
                                ex.getExerciseTemplateidEntity().getId(),
                                ex.getExerciseTemplateidEntity().getName(),
                                ex.getOrderInWorkout(),
                                ex.getPlannedSets(),
                                ex.getPlannedReps(),
                                ex.getPlannedWeight(),
                                ex.getNotes());
        }

        protected com.fitnesstrackerbackend.domain.trainingplan.dto.UserTrainingPlanDto mapToUserPlanDto(
                        com.fitnesstrackerbackend.domain.trainingplan.model.UserTrainingPlanEntity userPlan) {
                return com.fitnesstrackerbackend.domain.trainingplan.dto.UserTrainingPlanDto.builder()
                                .id(userPlan.getId())
                                .plan(mapToDto(userPlan.getTrainingPlanidEntity()))
                                .startDate(userPlan.getStartDate())
                                .endDate(userPlan.getEndDate())
                                .status(userPlan.getStatus())
                                .planTitle(userPlan.getPlanTitle())
                                .build();
        }
}
