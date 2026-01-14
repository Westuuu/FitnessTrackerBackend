package com.fitnesstrackerbackend.domain.session.repositories;

import com.fitnesstrackerbackend.domain.session.model.ExerciseInstanceSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseInstanceSetRepository extends JpaRepository<ExerciseInstanceSetEntity, Long> {
    List<ExerciseInstanceSetEntity> findByExerciseInstanceidEntity_Id(Long instanceId);
}
