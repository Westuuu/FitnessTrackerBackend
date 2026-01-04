package com.fitnesstrackerbackend.domain.gym;

import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import com.fitnesstrackerbackend.domain.gym.model.GymEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<GymEntity, Long> {
}
