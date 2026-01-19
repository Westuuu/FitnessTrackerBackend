package com.fitnesstrackerbackend.domain.session.repositories;

import com.fitnesstrackerbackend.domain.session.model.ExerciseInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseInstanceRepository extends JpaRepository<ExerciseInstanceEntity, Long> {
    @Query("SELECT ei FROM ExerciseInstanceEntity ei " +
            "JOIN FETCH ei.userWorkoutExerciseidEntity uwe " +
            "JOIN FETCH uwe.exerciseTemplateidEntity " +
            "WHERE uwe.workoutSessionidEntity.id = :sessionId")
    List<ExerciseInstanceEntity> findByUserWorkoutExerciseidEntity_WorkoutSessionidEntity_Id(
            @Param("sessionId") Long sessionId);
}
