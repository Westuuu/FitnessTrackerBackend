package com.fitnesstrackerbackend.domain.goal;

import com.fitnesstrackerbackend.core.exception.BusinessLogicException;
import com.fitnesstrackerbackend.domain.goal.dto.GoalDto;
import com.fitnesstrackerbackend.domain.goal.model.GoalEntity;
import com.fitnesstrackerbackend.domain.goal.repositories.GoalRepository;
import com.fitnesstrackerbackend.domain.session.repositories.ExerciseInstanceSetRepository;
import com.fitnesstrackerbackend.domain.trainingplan.model.ExerciseTemplateEntity;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private ExerciseInstanceSetRepository exerciseInstanceSetRepository;

    @InjectMocks
    private GoalService goalService;

    private UserEntity user;
    private ExerciseTemplateEntity exercise;
    private GoalEntity goal;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);

        exercise = new ExerciseTemplateEntity();
        exercise.setId(10L);
        exercise.setName("Bench Press");

        goal = new GoalEntity();
        goal.setId(100);
        goal.setUserid(user);
        goal.setExerciseTemplateidEntity(exercise);
        goal.setTargetValue(new BigDecimal("100.00"));
        goal.setCurrentValue(new BigDecimal("80.00"));
        goal.setStatus("IN_PROGRESS");
    }

    @Test
    void shouldUpdateGoalProgressWhenNewMaxWeightFound() {
        // Given
        BigDecimal newMaxWeight = new BigDecimal("110.00");
        when(goalRepository.findByUserid_Id(1L)).thenReturn(Collections.singletonList(goal));
        when(exerciseInstanceSetRepository.findMaxWeightForExerciseByUser(10L, 1L))
                .thenReturn(Optional.of(newMaxWeight));

        // When
        List<GoalDto> result = goalService.getGoalsByUserId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals(newMaxWeight, result.get(0).currentValue());
        assertEquals("ACHIEVED", result.get(0).status());
        verify(goalRepository, times(1)).save(goal);
    }

    @Test
    void shouldNotUpdateGoalWhenWeightHasNotChanged() {
        // Given
        BigDecimal sameWeight = new BigDecimal("80.00");
        when(goalRepository.findByUserid_Id(1L)).thenReturn(Collections.singletonList(goal));
        when(exerciseInstanceSetRepository.findMaxWeightForExerciseByUser(10L, 1L))
                .thenReturn(Optional.of(sameWeight));

        // When
        List<GoalDto> result = goalService.getGoalsByUserId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals(sameWeight, result.get(0).currentValue());
        verify(goalRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenDeletingGoalOfAnotherUser() {
        // Given
        when(goalRepository.findById(100)).thenReturn(Optional.of(goal));

        // When & Then
        assertThrows(BusinessLogicException.class, () -> goalService.deleteGoal(100, 2L) // User ID 2 is not the owner
        );
        verify(goalRepository, never()).delete(any());
    }
}
