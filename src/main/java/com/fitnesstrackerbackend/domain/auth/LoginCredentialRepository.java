package com.fitnesstrackerbackend.domain.auth;

import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface LoginCredentialRepository extends JpaRepository<LoginCredentialEntity, Long> {
    Optional<LoginCredentialEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
