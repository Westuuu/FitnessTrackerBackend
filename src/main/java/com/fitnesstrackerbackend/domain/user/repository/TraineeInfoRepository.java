package com.fitnesstrackerbackend.domain.user.repository;

import com.fitnesstrackerbackend.domain.user.model.TraineeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineeInfoRepository extends JpaRepository<TraineeInfoEntity, Long> {
}
