package com.fitnesstrackerbackend.domain.session.repositories;

import com.fitnesstrackerbackend.domain.session.model.ExerciseInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseInstanceRepository extends JpaRepository<ExerciseInstanceEntity, Long> {
    List<ExerciseInstanceEntity> findByUserWorkoutExerciseidEntity_WorkoutSessionidEntity_Id(Long sessionId);
}
