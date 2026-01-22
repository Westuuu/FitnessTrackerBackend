package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.domain.trainingplan.model.TrainingPlanEntity;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.TrainingPlanRepository;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.UserTrainingPlanRepository;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingPlanServiceTest {

    @Mock
    private TrainingPlanRepository trainingPlanRepository;

    @Mock
    private UserTrainingPlanRepository userTrainingPlanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingPlanMapper trainingPlanMapper;

    @InjectMocks
    private TrainingPlanService trainingPlanService;

    @Test
    void shouldDenyAssigningPlanNotOwnedByTrainer() {
        // Given
        Long trainerId = 1L;
        Long traineeId = 2L;
        Long planId = 10L;

        // Mock that the plan exists but is NOT created by this trainer
        when(trainingPlanRepository.existsByIdAndCreatedBy(planId, trainerId)).thenReturn(false);

        // When & Then
        assertThrows(AccessDeniedException.class,
                () -> trainingPlanService.assignTrainerPlanToTrainee(trainerId, traineeId, planId));

        verify(userTrainingPlanRepository, never()).save(any());
    }

    @Test
    void shouldAllowAssigningPlanOwnedByTrainer() {
        // Given
        Long trainerId = 1L;
        Long traineeId = 2L;
        Long planId = 10L;
        TrainingPlanEntity plan = new TrainingPlanEntity();
        plan.setName("Trainer Plan");

        when(trainingPlanRepository.existsByIdAndCreatedBy(planId, trainerId)).thenReturn(true);
        when(trainingPlanRepository.findById(planId)).thenReturn(Optional.of(plan));

        // When
        trainingPlanService.assignTrainerPlanToTrainee(trainerId, traineeId, planId);

        // Then
        verify(userTrainingPlanRepository, times(1)).save(any());
    }
}
