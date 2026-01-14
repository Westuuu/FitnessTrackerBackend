package com.fitnesstrackerbackend.domain.user.repository;

import com.fitnesstrackerbackend.domain.user.model.MembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<MembershipEntity, Long> {
    @Query("SELECT m FROM MembershipEntity m WHERE m.traineeInfoid.id = :traineeId AND m.membershipStatus = 'ACTIVE'")
    Optional<MembershipEntity> findActiveByTraineeId(@Param("traineeId") Long traineeId);
}
