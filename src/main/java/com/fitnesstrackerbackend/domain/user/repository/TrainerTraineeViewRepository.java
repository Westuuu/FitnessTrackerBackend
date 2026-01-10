package com.fitnesstrackerbackend.domain.user.repository;

import com.fitnesstrackerbackend.domain.user.model.TrainerTraineeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerTraineeViewRepository extends JpaRepository<TrainerTraineeView, Long> {
    List<TrainerTraineeView> findByTrainerId(Long trainerId);
}
