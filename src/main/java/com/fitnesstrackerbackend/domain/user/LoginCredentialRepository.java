package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.domain.user.model.LoginCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface LoginCredentialRepository extends JpaRepository<LoginCredentialEntity, Long> {
    Optional<LoginCredentialEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
