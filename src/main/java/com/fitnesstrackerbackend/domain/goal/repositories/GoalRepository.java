package com.fitnesstrackerbackend.domain.goal.repositories;

import com.fitnesstrackerbackend.domain.goal.model.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, Integer> {
    List<GoalEntity> findByUserid_Id(Long userId);

    List<GoalEntity> findByUserid_IdAndStatus(Long userId, String status);
}
