package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.domain.trainingplan.dto.*;
import com.fitnesstrackerbackend.domain.trainingplan.enums.VisibilityType;
import com.fitnesstrackerbackend.domain.trainingplan.exceptions.TrainingPlanNotFoundException;
import com.fitnesstrackerbackend.domain.trainingplan.model.*;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.*;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingPlanService {

    private final TrainingPlanRepository trainingPlanRepository;
    private final TrainingPlanMapper trainingPlanMapper;
    private final WorkoutTemplateDayRepository workoutTemplateDayRepository;
    private final WorkoutTemplateExerciseRepository workoutTemplateExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final TrainingPlanRoleRepository trainingPlanRoleRepository;
    private final UserRepository userRepository;
    private final UserTrainingPlanRepository userTrainingPlanRepository;

    @Transactional
    public TrainingPlanDto createTrainingPlan(Long userId, TrainingPlanCreateDto createDto) {
        TrainingPlanEntity plan = new TrainingPlanEntity();
        plan.setName(createDto.name());
        plan.setDescription(createDto.description());
        plan.setDifficultyLevel(createDto.difficultyLevel());
        plan.setDurationWeeks(createDto.durationWeeks());
        plan.setVisibilityType(createDto.visibilityType());
        plan.setCreatedAt(java.time.Instant.now());
        plan.setUpdatedAt(java.time.Instant.now());

        TrainingPlanEntity savedPlan = trainingPlanRepository.save(plan);

        // Assign OWNER role
        TrainingPlanRoleEntity role = new TrainingPlanRoleEntity();
        role.setTrainingPlan(savedPlan);
        role.setUserid(userRepository.getReferenceById(userId));
        role.setRole("OWNER");
        role.setGrantedAt(java.time.Instant.now());
        trainingPlanRoleRepository.save(role);

        // Process Workout Days
        if (createDto.workoutDays() != null) {
            for (var dayDto : createDto.workoutDays()) {
                WorkoutTemplateDayEntity day = new WorkoutTemplateDayEntity();
                day.setTrainingPlanidEntity(savedPlan);
                day.setName(dayDto.name());
                day.setDayOfWeek(dayDto.dayOfWeek());
                day.setNotes(dayDto.notes());
                var savedDay = workoutTemplateDayRepository.save(day);

                // Process Exercises
                if (dayDto.exercises() != null) {
                    for (var exDto : dayDto.exercises()) {
                        WorkoutTemplateExerciseEntity ex = new WorkoutTemplateExerciseEntity();
                        ex.setWorkoutTemplateid(savedDay);
                        ex.setExerciseTemplateidEntity(exerciseRepository.getReferenceById(exDto.exerciseTemplateId()));
                        ex.setOrderInWorkout(exDto.orderInWorkout());
                        ex.setPlannedSets(exDto.plannedSets());
                        ex.setPlannedReps(exDto.plannedReps());
                        ex.setPlannedWeight(
                                exDto.plannedWeight() != null ? exDto.plannedWeight() : java.math.BigDecimal.ZERO);
                        ex.setNotes(exDto.notes());
                        workoutTemplateExerciseRepository.save(ex);
                    }
                }
            }
        }

        return trainingPlanMapper.mapToDto(savedPlan);
    }

    @Transactional
    public com.fitnesstrackerbackend.domain.trainingplan.dto.UserTrainingPlanDto assignPlanToUser(Long userId,
            Long planId) {
        TrainingPlanEntity plan = trainingPlanRepository.findById(planId)
                .orElseThrow(() -> new TrainingPlanNotFoundException(planId));

        UserTrainingPlanEntity userPlan = new UserTrainingPlanEntity();
        userPlan.setUserid(userRepository.getReferenceById(userId));
        userPlan.setTrainingPlanidEntity(plan);
        userPlan.setStartDate(java.time.LocalDate.now());
        userPlan.setStatus("ACTIVE");
        userPlan.setPlanTitle(plan.getName());

        return trainingPlanMapper.mapToUserPlanDto(userTrainingPlanRepository.save(userPlan));
    }

    @Transactional
    public com.fitnesstrackerbackend.domain.trainingplan.dto.UserTrainingPlanDto assignTrainerPlanToTrainee(
            Long trainerId, Long traineeId, Long planId) {
        if (!trainingPlanRepository.existsByIdAndCreatedBy(planId, trainerId)) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "You can only assign plans you created.");
        }
        return assignPlanToUser(traineeId, planId);
    }

    @Transactional(readOnly = true)
    public List<com.fitnesstrackerbackend.domain.trainingplan.dto.UserTrainingPlanDto> getMyTrainingPlans(Long userId) {
        return userTrainingPlanRepository.findByUserid_Id(userId).stream()
                .map(trainingPlanMapper::mapToUserPlanDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TrainingPlanDto> getCreatedPlans(Long userId) {
        return trainingPlanRepository.findCreatedPlans(userId).stream()
                .map(trainingPlanMapper::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TrainingPlanSummaryDto> getAllTrainingPlans(VisibilityType visibilityType, Long userId) {
        List<TrainingPlanEntity> plans;

        if (visibilityType != null) {
            plans = trainingPlanRepository.findAccessiblePlansByVisibility(
                    userId,
                    visibilityType.name());
        } else {
            plans = trainingPlanRepository.findAllAccessiblePlans(userId);
        }

        return plans.stream()
                .map(trainingPlanMapper::mapToSummaryDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrainingPlanDto getTrainingPlanById(Long trainingPlanId, Long userId) {
        TrainingPlanEntity trainingPlan = trainingPlanRepository.findAccessibleById(trainingPlanId, userId)
                .orElseThrow(() -> new TrainingPlanNotFoundException(trainingPlanId));

        return trainingPlanMapper.mapToDto(trainingPlan);
    }

    // Exercise related methods
    @Transactional(readOnly = true)
    public List<ExerciseTemplateDto> getExerciseTemplates() {
        return exerciseRepository.findAll().stream()
                .map(trainingPlanMapper::mapToExerciseTemplateDto)
                .toList();
    }

    @Transactional
    public ExerciseTemplateDto createExerciseTemplate(ExerciseTemplateCreationDto exerciseTemplateCreationDto) {
        ExerciseTemplateEntity exerciseTemplate = trainingPlanMapper.mapToExerciseEntity(exerciseTemplateCreationDto);
        ExerciseTemplateEntity savedExerciseTemplate = exerciseRepository.save(exerciseTemplate);
        return trainingPlanMapper.mapToExerciseTemplateDto(savedExerciseTemplate);
    }
}
