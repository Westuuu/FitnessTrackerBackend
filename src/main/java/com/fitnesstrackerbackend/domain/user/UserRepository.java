package com.fitnesstrackerbackend.domain.user;


import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.traineeInfo " +
            "LEFT JOIN FETCH u.trainerInfo " +
            "LEFT JOIN FETCH u.adminInfo " +
            "WHERE u.id = :id")
    Optional<UserEntity> findByIdForAdmin(@Param("id") Long id);

    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.traineeInfo " +
            "LEFT JOIN FETCH u.trainerInfo " +
            "WHERE u.id = :id")
    Optional<UserEntity> findByIdForNonAdmin(@Param("id") Long id);

    boolean existsByLoginCredentialEmail(String loginCredentialEmail);

    @Query("SELECT u FROM UserEntity u " +
            "JOIN FETCH u.loginCredential lc " +
            "WHERE lc.email = :email")
    Optional<UserEntity> findByLoginCredentialEmail(String email);

}
