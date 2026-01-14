package com.fitnesstrackerbackend.domain.trainingplan.repositories;

import com.fitnesstrackerbackend.domain.trainingplan.model.WorkoutTemplateExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutTemplateExerciseRepository extends JpaRepository<WorkoutTemplateExerciseEntity, Long> {
    List<WorkoutTemplateExerciseEntity> findByWorkoutTemplateid_Id(Long workoutTemplateId);
}
