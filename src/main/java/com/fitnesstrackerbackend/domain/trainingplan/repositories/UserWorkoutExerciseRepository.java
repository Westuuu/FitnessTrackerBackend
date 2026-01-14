package com.fitnesstrackerbackend.domain.trainingplan.repositories;

import com.fitnesstrackerbackend.domain.trainingplan.model.UserWorkoutExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkoutExerciseRepository extends JpaRepository<UserWorkoutExerciseEntity, Long> {
    List<UserWorkoutExerciseEntity> findByWorkoutSessionidEntity_Id(Long sessionId);
}
