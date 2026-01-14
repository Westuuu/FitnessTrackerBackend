package com.fitnesstrackerbackend.domain.trainingplan.repositories;

import com.fitnesstrackerbackend.domain.trainingplan.model.UserTrainingPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTrainingPlanRepository extends JpaRepository<UserTrainingPlanEntity, Long> {
    List<UserTrainingPlanEntity> findByUserid_Id(Long userId);

    List<UserTrainingPlanEntity> findByUserid_IdAndStatus(Long userId, String status);

    Optional<UserTrainingPlanEntity> findByUserid_IdAndTrainingPlanidEntity_Id(Long userId, Long trainingPlanId);
}
