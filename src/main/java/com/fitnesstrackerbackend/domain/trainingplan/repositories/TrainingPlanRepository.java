package com.fitnesstrackerbackend.domain.trainingplan.repositories;

import com.fitnesstrackerbackend.domain.trainingplan.model.TrainingPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlanEntity, Long> {
  @Query("""
          SELECT DISTINCT tp FROM TrainingPlanEntity tp
          LEFT JOIN TrainingPlanRoleEntity tpr ON tpr.trainingPlan.id = tp.id AND tpr.userid.id = :userId
          WHERE tp.visibilityType = :visibility
            AND (tpr.id IS NOT NULL OR tp.visibilityType = 'PUBLIC')
          ORDER BY tp.createdAt DESC
      """)
  List<TrainingPlanEntity> findAccessiblePlansByVisibility(
      @Param("userId") Long userId,
      @Param("visibility") String visibility);

  @Query("""
          SELECT DISTINCT tp FROM TrainingPlanEntity tp
          LEFT JOIN TrainingPlanRoleEntity tpr ON tpr.trainingPlan.id = tp.id AND tpr.userid.id = :userId
          WHERE tpr.id IS NOT NULL
             OR tp.visibilityType = 'PUBLIC'
          ORDER BY tp.createdAt DESC
      """)
  List<TrainingPlanEntity> findAllAccessiblePlans(@Param("userId") Long userId);

  @Query("""
      SELECT tp FROM TrainingPlanEntity tp
      LEFT JOIN TrainingPlanRoleEntity tpr ON tpr.trainingPlan.id = tp.id AND tpr.userid.id = :userId
      WHERE tp.id = :trainingPlanId
        AND (tpr.id IS NOT NULL OR tp.visibilityType = 'PUBLIC')
      """)
  Optional<TrainingPlanEntity> findAccessibleById(
      @Param("trainingPlanId") Long trainingPlanId,
      @Param("userId") Long userId);

  @Query("""
      SELECT tp FROM TrainingPlanEntity tp
      JOIN TrainingPlanRoleEntity tpr ON tp.id = tpr.trainingPlan.id
      WHERE tpr.userid.id = :userId
        AND tpr.role = 'OWNER'
      ORDER BY tp.createdAt DESC
      """)
  List<TrainingPlanEntity> findCreatedPlans(@Param("userId") Long userId);

  @Query("""
      SELECT COUNT(tp) > 0 FROM TrainingPlanEntity tp
      JOIN TrainingPlanRoleEntity tpr ON tp.id = tpr.trainingPlan.id
      WHERE tp.id = :planId
        AND tpr.userid.id = :userId
        AND tpr.role = 'OWNER'
      """)
  boolean existsByIdAndCreatedBy(@Param("planId") Long planId, @Param("userId") Long userId);
}
