package com.fitnesstrackerbackend.domain.session.repositories;

import com.fitnesstrackerbackend.domain.session.model.WorkoutSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSessionEntity, Long> {
    Optional<WorkoutSessionEntity> findByUserTrainingPlanidEntity_Userid_IdAndCompleted(Long userId, Boolean completed);

    List<WorkoutSessionEntity> findByUserTrainingPlanidEntity_Userid_IdOrderBySessionDateDesc(Long userId);
}
