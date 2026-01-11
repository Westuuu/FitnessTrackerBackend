package com.fitnesstrackerbackend.domain.trainingplan.exceptions;

import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;

public class TrainingPlanNotFoundException extends ResourceNotFoundException {
    public TrainingPlanNotFoundException(Long trainingPlanId) {
        super("Training plan with id " + trainingPlanId + " was not found");
    }
}
