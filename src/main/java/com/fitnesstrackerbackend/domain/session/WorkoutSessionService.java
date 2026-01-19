package com.fitnesstrackerbackend.domain.session;

import com.fitnesstrackerbackend.domain.session.dto.ExerciseInstanceDto;
import com.fitnesstrackerbackend.domain.session.dto.ExerciseSetDto;
import com.fitnesstrackerbackend.domain.session.dto.WorkoutSessionDto;
import com.fitnesstrackerbackend.domain.session.model.ExerciseInstanceEntity;
import com.fitnesstrackerbackend.domain.session.model.ExerciseInstanceSetEntity;
import com.fitnesstrackerbackend.domain.session.model.WorkoutSessionEntity;
import com.fitnesstrackerbackend.domain.session.repositories.ExerciseInstanceRepository;
import com.fitnesstrackerbackend.domain.session.repositories.ExerciseInstanceSetRepository;
import com.fitnesstrackerbackend.domain.session.repositories.WorkoutSessionRepository;
import com.fitnesstrackerbackend.domain.trainingplan.model.UserTrainingPlanEntity;
import com.fitnesstrackerbackend.domain.trainingplan.model.UserWorkoutExerciseEntity;
import com.fitnesstrackerbackend.domain.trainingplan.model.WorkoutTemplateExerciseEntity;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.ExerciseRepository;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.UserTrainingPlanRepository;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.UserWorkoutExerciseRepository;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.WorkoutTemplateExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final ExerciseInstanceRepository exerciseInstanceRepository;
    private final ExerciseInstanceSetRepository exerciseInstanceSetRepository;
    private final UserTrainingPlanRepository userTrainingPlanRepository;
    private final UserWorkoutExerciseRepository userWorkoutExerciseRepository;
    private final WorkoutTemplateExerciseRepository workoutTemplateExerciseRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional
    public WorkoutSessionDto startSession(Long userId, Long userTrainingPlanId, Long workoutTemplateId) {
        UserTrainingPlanEntity userPlan = userTrainingPlanRepository.findById(userTrainingPlanId)
                .orElseThrow(() -> new RuntimeException("Training plan not found"));

        if (!userPlan.getUserid().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        // Check for active session
        workoutSessionRepository.findByUserTrainingPlanidEntity_Userid_IdAndCompleted(userId, false)
                .ifPresent(s -> {
                    throw new RuntimeException("Session already in progress");
                });

        WorkoutSessionEntity session = new WorkoutSessionEntity();
        session.setUserTrainingPlanidEntity(userPlan);
        session.setSessionDate(LocalDate.now());
        session.setStartTime(LocalTime.now());
        session.setCompleted(false);
        session.setUpdatedAt(Instant.now());

        WorkoutSessionEntity savedSession = workoutSessionRepository.save(session);

        // Fetch template exercises
        List<WorkoutTemplateExerciseEntity> templateExercises = workoutTemplateExerciseRepository
                .findByWorkoutTemplateid_Id(workoutTemplateId);

        for (WorkoutTemplateExerciseEntity templateEx : templateExercises) {
            // Create UserWorkoutExercise
            UserWorkoutExerciseEntity userWorkoutEx = new UserWorkoutExerciseEntity();
            userWorkoutEx.setWorkoutSessionidEntity(savedSession);
            userWorkoutEx.setExerciseTemplateidEntity(templateEx.getExerciseTemplateidEntity());
            userWorkoutEx.setOrderInWorkout(templateEx.getOrderInWorkout());
            userWorkoutEx.setPlannedSets(templateEx.getPlannedSets());
            userWorkoutEx.setPlannedReps(templateEx.getPlannedReps());
            userWorkoutEx.setPlannedWeight(templateEx.getPlannedWeight());
            userWorkoutExerciseRepository.save(userWorkoutEx);

            // Create ExerciseInstance
            ExerciseInstanceEntity instance = new ExerciseInstanceEntity();
            instance.setUserWorkoutExerciseidEntity(userWorkoutEx);
            instance.setOrderInWorkout(templateEx.getOrderInWorkout());
            instance.setCompleted(false);
            ExerciseInstanceEntity savedInstance = exerciseInstanceRepository.save(instance);

            // Create Sets
            for (int i = 1; i <= templateEx.getPlannedSets(); i++) {
                ExerciseInstanceSetEntity set = new ExerciseInstanceSetEntity();
                set.setExerciseInstanceidEntity(savedInstance);
                set.setSetNumber(i);
                set.setReps(templateEx.getPlannedReps());
                set.setWeight(templateEx.getPlannedWeight());
                set.setCompleted(false);
                exerciseInstanceSetRepository.save(set);
            }
        }

        return getSessionDto(savedSession.getId());
    }

    @Transactional(readOnly = true)
    public WorkoutSessionDto getActiveSession(Long userId) {
        return workoutSessionRepository.findByUserTrainingPlanidEntity_Userid_IdAndCompleted(userId, false)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public WorkoutSessionDto getSessionDto(Long sessionId) {
        WorkoutSessionEntity session = workoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return mapToDto(session);
    }

    private WorkoutSessionDto mapToDto(WorkoutSessionEntity session) {
        Long sessionId = session.getId();
        List<ExerciseInstanceEntity> instances = exerciseInstanceRepository
                .findByUserWorkoutExerciseidEntity_WorkoutSessionidEntity_Id(sessionId);

        List<ExerciseInstanceDto> exerciseDtos = instances.stream().map(instance -> {
            List<ExerciseInstanceSetEntity> sets = exerciseInstanceSetRepository
                    .findByExerciseInstanceidEntity_Id(instance.getId());
            List<ExerciseSetDto> setDtos = sets.stream()
                    .map(set -> new ExerciseSetDto(set.getId(), set.getSetNumber(), set.getReps(), set.getWeight(),
                            set.getCompleted()))
                    .collect(Collectors.toList());

            return new ExerciseInstanceDto(
                    instance.getId(),
                    instance.getUserWorkoutExerciseidEntity().getExerciseTemplateidEntity().getId(),
                    instance.getUserWorkoutExerciseidEntity().getExerciseTemplateidEntity().getName(),
                    instance.getOrderInWorkout(),
                    instance.getCompleted(),
                    setDtos);
        }).collect(Collectors.toList());

        return new WorkoutSessionDto(
                session.getId(),
                session.getUserTrainingPlanidEntity().getId(),
                session.getUserTrainingPlanidEntity().getPlanTitle(),
                session.getSessionDate(),
                session.getStartTime(),
                session.getEndTime(),
                session.getNotes(),
                session.getCompleted(),
                exerciseDtos);
    }

    @Transactional
    public void finishSession(Long sessionId, Long userId, String notes) {
        WorkoutSessionEntity session = workoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (!session.getUserTrainingPlanidEntity().getUserid().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        session.setCompleted(true);
        session.setEndTime(LocalTime.now());
        session.setNotes(notes);
        session.setUpdatedAt(Instant.now());
        workoutSessionRepository.save(session);
    }

    @Transactional
    public void updateSet(Long setId, Long userId, Integer reps, java.math.BigDecimal weight, Boolean completed) {
        ExerciseInstanceSetEntity set = exerciseInstanceSetRepository.findById(setId)
                .orElseThrow(() -> new RuntimeException("Set not found"));

        if (!set.getExerciseInstanceidEntity().getUserWorkoutExerciseidEntity().getWorkoutSessionidEntity()
                .getUserTrainingPlanidEntity().getUserid().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        if (reps != null)
            set.setReps(reps);
        if (weight != null)
            set.setWeight(weight);
        if (completed != null) {
            set.setCompleted(completed);

            // Check if all sets for this instance are completed
            ExerciseInstanceEntity instance = set.getExerciseInstanceidEntity();
            List<ExerciseInstanceSetEntity> allSets = exerciseInstanceSetRepository
                    .findByExerciseInstanceidEntity_Id(instance.getId());
            boolean allCompleted = allSets.stream().allMatch(ExerciseInstanceSetEntity::getCompleted);
            instance.setCompleted(allCompleted);
            exerciseInstanceRepository.save(instance);
        }

        exerciseInstanceSetRepository.save(set);
    }

    @Transactional
    public void saveSession(Long userId,
            com.fitnesstrackerbackend.domain.session.dto.WorkoutSessionCreateDto createDto) {
        UserTrainingPlanEntity userPlan = userTrainingPlanRepository.findByUserid_Id(userId).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No active training plan found. Please assign one first."));

        WorkoutSessionEntity session = new WorkoutSessionEntity();
        session.setUserTrainingPlanidEntity(userPlan);
        session.setSessionDate(LocalDate.now());
        session.setStartTime(LocalTime.now().minusHours(1));
        session.setEndTime(LocalTime.now());
        session.setCompleted(true);
        session.setNotes(createDto.notes());
        session.setUpdatedAt(Instant.now());

        WorkoutSessionEntity savedSession = workoutSessionRepository.save(session);

        for (int i = 0; i < createDto.exercises().size(); i++) {
            var exDto = createDto.exercises().get(i);

            // Try to find exercise template by name
            com.fitnesstrackerbackend.domain.trainingplan.model.ExerciseTemplateEntity template = exerciseRepository
                    .findByName(exDto.exerciseName())
                    .orElseThrow(() -> new RuntimeException("Exercise template not found: " + exDto.exerciseName()));

            UserWorkoutExerciseEntity userWorkoutEx = new UserWorkoutExerciseEntity();
            userWorkoutEx.setWorkoutSessionidEntity(savedSession);
            userWorkoutEx.setExerciseTemplateidEntity(template);
            userWorkoutEx.setOrderInWorkout(i + 1);
            userWorkoutEx.setPlannedSets(exDto.sets().size());
            userWorkoutEx.setPlannedReps(exDto.sets().get(0).reps());
            userWorkoutEx.setPlannedWeight(exDto.sets().get(0).weight());
            userWorkoutExerciseRepository.save(userWorkoutEx);

            ExerciseInstanceEntity instance = new ExerciseInstanceEntity();
            instance.setUserWorkoutExerciseidEntity(userWorkoutEx);
            instance.setOrderInWorkout(i + 1);
            instance.setCompleted(true);
            ExerciseInstanceEntity savedInstance = exerciseInstanceRepository.save(instance);

            for (var setDto : exDto.sets()) {
                ExerciseInstanceSetEntity set = new ExerciseInstanceSetEntity();
                set.setExerciseInstanceidEntity(savedInstance);
                set.setSetNumber(setDto.setNumber());
                set.setReps(setDto.reps());
                set.setWeight(setDto.weight());
                set.setCompleted(true);
                exerciseInstanceSetRepository.save(set);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<WorkoutSessionDto> getSessionHistory(Long userId) {
        return workoutSessionRepository.findByUserTrainingPlanidEntity_Userid_IdOrderBySessionDateDesc(userId).stream()
                .map(this::mapToDto)
                .collect(java.util.stream.Collectors.toList());
    }
}
