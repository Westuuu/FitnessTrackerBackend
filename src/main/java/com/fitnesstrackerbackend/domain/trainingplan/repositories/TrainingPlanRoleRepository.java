package com.fitnesstrackerbackend.domain.trainingplan.repositories;

import com.fitnesstrackerbackend.domain.trainingplan.model.TrainingPlanRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanRoleRepository extends JpaRepository<TrainingPlanRoleEntity, Long> {
}
