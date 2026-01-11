package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.trainingplan.dto.TrainingPlanSummaryDto;
import com.fitnesstrackerbackend.domain.trainingplan.enums.VisibilityType;
import com.fitnesstrackerbackend.domain.trainingplan.exceptions.TrainingPlanNotFoundException;
import com.fitnesstrackerbackend.domain.trainingplan.model.TrainingPlanEntity;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.TrainingPlanRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrainingPlanService {

    private final TrainingPlanRepository trainingPlanRepository;
    private final TrainingPlanMapper trainingPlanMapper;

    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository, TrainingPlanMapper trainingPlanMapper) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.trainingPlanMapper = trainingPlanMapper;
    }

    @Transactional(readOnly = true)
    public List<TrainingPlanSummaryDto> getAllTrainingPlans(VisibilityType visibilityType, Long userId) {
        List<TrainingPlanEntity> plans;

        if (visibilityType != null) {
            plans = trainingPlanRepository.findAccessiblePlansByVisibility(
                    userId,
                    visibilityType.name()
            );
        } else {
            plans = trainingPlanRepository.findAllAccessiblePlans(userId);
        }

        return plans.stream()
                .map(trainingPlanMapper::mapToSummaryDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrainingPlanSummaryDto getTrainingPlanById(Long trainingPlanId, Long userId) {
        TrainingPlanEntity trainingPlan = trainingPlanRepository.findAccessibleById(trainingPlanId, userId)
                .orElseThrow(() -> new TrainingPlanNotFoundException(trainingPlanId));

        return trainingPlanMapper.mapToSummaryDto(trainingPlan);
    }
}
