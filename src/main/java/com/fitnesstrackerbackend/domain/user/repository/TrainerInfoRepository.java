package com.fitnesstrackerbackend.domain.user.repository;

import com.fitnesstrackerbackend.domain.user.model.TrainerInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerInfoRepository extends JpaRepository<TrainerInfoEntity, Long> {
}
