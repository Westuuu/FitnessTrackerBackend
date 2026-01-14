package com.fitnesstrackerbackend.domain.trainingplan.repositories;

import com.fitnesstrackerbackend.domain.trainingplan.model.WorkoutTemplateDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutTemplateDayRepository extends JpaRepository<WorkoutTemplateDayEntity, Long> {
    List<WorkoutTemplateDayEntity> findByTrainingPlanidEntity_Id(Long trainingPlanId);
}
