package com.fitnesstrackerbackend.domain.trainingplan.repositories;

import com.fitnesstrackerbackend.domain.trainingplan.model.ExerciseTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<ExerciseTemplateEntity, Long> {
}
