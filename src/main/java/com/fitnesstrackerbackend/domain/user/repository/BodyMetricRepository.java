package com.fitnesstrackerbackend.domain.user.repository;

import com.fitnesstrackerbackend.domain.user.model.BodyMetricEntity;
import com.fitnesstrackerbackend.domain.user.model.BodyMetricIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyMetricRepository extends JpaRepository<BodyMetricEntity, BodyMetricIdEntity> {
    List<BodyMetricEntity> findById_UseridOrderById_DateAsc(Long userId);
}
