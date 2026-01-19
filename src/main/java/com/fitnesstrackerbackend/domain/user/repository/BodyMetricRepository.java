package com.fitnesstrackerbackend.domain.user.repository;

import com.fitnesstrackerbackend.domain.user.model.BodyMetricEntity;
import com.fitnesstrackerbackend.domain.user.model.BodyMetricIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyMetricRepository extends JpaRepository<BodyMetricEntity, BodyMetricIdEntity> {
    @Query("SELECT bm FROM BodyMetricEntity bm WHERE bm.id.userid = :userId ORDER BY bm.id.date ASC")
    List<BodyMetricEntity> findById_UseridOrderById_DateAsc(@Param("userId") Long userId);
}
