package com.fitnesstrackerbackend.domain.user.repository;

import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.gym WHERE u.gym.id = :gymId")
    List<UserEntity> findAllByGymId(@Param("gymId") Long gymId);
}
