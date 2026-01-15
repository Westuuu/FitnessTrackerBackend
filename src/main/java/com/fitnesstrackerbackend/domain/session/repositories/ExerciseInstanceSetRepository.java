package com.fitnesstrackerbackend.domain.session.repositories;

import com.fitnesstrackerbackend.domain.session.model.ExerciseInstanceSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseInstanceSetRepository extends JpaRepository<ExerciseInstanceSetEntity, Long> {
    List<ExerciseInstanceSetEntity> findByExerciseInstanceidEntity_Id(Long instanceId);

    @Query("""
            SELECT MAX(eis.weight)
            FROM ExerciseInstanceSetEntity eis
            JOIN eis.exerciseInstanceidEntity ei
            JOIN ei.userWorkoutExerciseidEntity uwe
            JOIN uwe.workoutSessionidEntity ws
            WHERE uwe.exerciseTemplateidEntity.id = :exerciseTemplateId
            AND ws.userTrainingPlanidEntity.userid.id = :userId
            AND eis.completed = true
            """)
    Optional<BigDecimal> findMaxWeightForExerciseByUser(
            @Param("exerciseTemplateId") Long exerciseTemplateId,
            @Param("userId") Long userId);
}
