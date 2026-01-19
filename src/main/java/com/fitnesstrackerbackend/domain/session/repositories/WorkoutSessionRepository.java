package com.fitnesstrackerbackend.domain.session.repositories;

import com.fitnesstrackerbackend.domain.session.model.WorkoutSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSessionEntity, Long> {
    @Query("SELECT ws FROM WorkoutSessionEntity ws JOIN FETCH ws.userTrainingPlanidEntity utp JOIN utp.userid u WHERE u.id = :userId AND ws.completed = :completed")
    Optional<WorkoutSessionEntity> findByUserTrainingPlanidEntity_Userid_IdAndCompleted(@Param("userId") Long userId,
            @Param("completed") Boolean completed);

    @Query("SELECT ws FROM WorkoutSessionEntity ws JOIN FETCH ws.userTrainingPlanidEntity utp JOIN utp.userid u WHERE u.id = :userId ORDER BY ws.sessionDate DESC")
    List<WorkoutSessionEntity> findByUserTrainingPlanidEntity_Userid_IdOrderBySessionDateDesc(
            @Param("userId") Long userId);
}
