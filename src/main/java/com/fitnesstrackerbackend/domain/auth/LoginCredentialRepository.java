package com.fitnesstrackerbackend.domain.auth;

import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginCredentialRepository extends JpaRepository<LoginCredentialEntity, Long> {

    @EntityGraph(attributePaths = "user")
    @Query("SELECT lc FROM LoginCredentialEntity lc WHERE lc.email = :email")
    Optional<LoginCredentialEntity> findByEmailWithUser(@Param("email") String email);

    Optional<LoginCredentialEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
