package com.fitnesstrackerbackend.domain.goal;

import com.fitnesstrackerbackend.core.exception.BusinessLogicException;
import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.domain.goal.dto.GoalCreateDto;
import com.fitnesstrackerbackend.domain.goal.dto.GoalDto;
import com.fitnesstrackerbackend.domain.goal.model.GoalEntity;
import com.fitnesstrackerbackend.domain.goal.repositories.GoalRepository;
import com.fitnesstrackerbackend.domain.trainingplan.model.ExerciseTemplateEntity;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.ExerciseRepository;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;


    public List<GoalDto> getGoalsByUserId(Long userId) {
        return goalRepository.findByUserid_Id(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GoalDto createGoal(Long userId, GoalCreateDto createDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ExerciseTemplateEntity exercise = exerciseRepository.findById(createDto.exerciseTemplateId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise template not found"));

        GoalEntity goal = new GoalEntity();
        goal.setUserid(user);
        goal.setExerciseTemplateidEntity(exercise);
        goal.setTitle(createDto.title());
        goal.setDescription(createDto.description());
        goal.setTargetValue(createDto.targetValue());
        goal.setCurrentValue(java.math.BigDecimal.ZERO);
        goal.setTargetDate(createDto.targetDate());
        goal.setStartDate(LocalDate.now());
        goal.setStatus("IN_PROGRESS");
        goal.setCreatedAt(Instant.now());
        goal.setUpdatedAt(Instant.now());

        return mapToDto(goalRepository.save(goal));
    }

    @Transactional
    public void deleteGoal(Integer goalId, Long userId) {
        GoalEntity goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

//        TODO: fix this
        if (!goal.getUserid().getId().equals(userId)) {
            throw new BusinessLogicException("Not authorized to delete this goal");
        }

        goalRepository.delete(goal);
    }

    private GoalDto mapToDto(GoalEntity entity) {
        return new GoalDto(
                entity.getId(),
                entity.getExerciseTemplateidEntity().getId(),
                entity.getExerciseTemplateidEntity().getName(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getTargetValue(),
                entity.getCurrentValue(),
                entity.getStartDate(),
                entity.getTargetDate(),
                entity.getStatus());
    }
}
