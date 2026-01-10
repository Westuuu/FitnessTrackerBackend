package com.fitnesstrackerbackend.domain.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "membership", indexes = {
        @Index(name = "unique_active_membership",
                columnList = "trainee_infoid",
                unique = true),
        @Index(name = "idx_membership_trainee_status",
                columnList = "trainee_infoid, membership_status"),
        @Index(name = "idx_membership_dates",
                columnList = "membership_start_date, membership_end_date")})
public class MembershipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainee_infoid", nullable = false)
    private TraineeInfoEntity traineeInfoid;

    @Size(max = 20)
    @NotNull
    @Column(name = "membership_status", nullable = false, length = 20)
    private String membershipStatus;

    @NotNull
    @Column(name = "membership_start_date", nullable = false)
    private LocalDate membershipStartDate;

    @Column(name = "membership_end_date")
    private LocalDate membershipEndDate;


}