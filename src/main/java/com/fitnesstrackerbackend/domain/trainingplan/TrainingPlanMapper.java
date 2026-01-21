package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.domain.trainingplan.dto.*;
import com.fitnesstrackerbackend.domain.trainingplan.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.time.Instant;

@Component
public class TrainingPlanMapper {
        public TrainingPlanSummaryDto mapToSummaryDto(TrainingPlanEntity trainingPlan) {
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

        public TrainingPlanDto mapToDto(TrainingPlanEntity trainingPlan) {
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

        public WorkoutDayDto mapToDayDto(WorkoutTemplateDayEntity day) {
                return new WorkoutDayDto(
                                day.getName(),
                                day.getDayOfWeek(),
                                day.getNotes(),
                                day.getExercises() != null
                                                ? day.getExercises().stream().map(this::mapToExerciseDto).toList()
                                                : java.util.Collections.emptyList());
        }

        public WorkoutExerciseDto mapToExerciseDto(WorkoutTemplateExerciseEntity ex) {
                return new WorkoutExerciseDto(
                                ex.getExerciseTemplateidEntity().getId(),
                                ex.getExerciseTemplateidEntity().getName(),
                                ex.getOrderInWorkout(),
                                ex.getPlannedSets(),
                                ex.getPlannedReps(),
                                ex.getPlannedWeight(),
                                ex.getNotes());
        }

        public UserTrainingPlanDto mapToUserPlanDto(UserTrainingPlanEntity userPlan) {
                return UserTrainingPlanDto.builder()
                                .id(userPlan.getId())
                                .plan(mapToDto(userPlan.getTrainingPlanidEntity()))
                                .startDate(userPlan.getStartDate())
                                .endDate(userPlan.getEndDate())
                                .status(userPlan.getStatus())
                                .planTitle(userPlan.getPlanTitle())
                                .build();
        }

        public ExerciseTemplateDto mapToExerciseTemplateDto(ExerciseTemplateEntity exerciseTemplate) {
                return ExerciseTemplateDto.builder()
                                .id(exerciseTemplate.getId())
                                .name(exerciseTemplate.getName())
                                .description(exerciseTemplate.getDescription())
                                .instructions(exerciseTemplate.getInstructions())
                                .equipmentNeeded(exerciseTemplate.getEquipmentNeeded())
                                .muscleGroup(exerciseTemplate.getMuscleGroup())
                                .build();
        }

        public ExerciseTemplateEntity mapToExerciseEntity(ExerciseTemplateCreationDto templateCreationDto) {
                return ExerciseTemplateEntity.builder()
                                .equipmentNeeded(templateCreationDto.equipmentNeeded())
                                .instructions(templateCreationDto.instructions())
                                .name(templateCreationDto.name())
                                .muscleGroup(templateCreationDto.muscleGroup())
                                .description(templateCreationDto.description())
                                .createdAt(Instant.now())
                                .build();
        }
}
