package com.fitnesstrackerbackend.domain.user.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "v_trainer_trainees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerTraineeView {
    @Id
    @Column(name = "trainee_id")
    private Long traineeId;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Column(name = "trainer_id")
    private Long trainerId;

    @Size(max = 255)
    @Column(name = "active_plan_name")
    private String activePlanName;

    @Size(max = 20)
    @Column(name = "membership_status", length = 20)
    private String membershipStatus;

    @Column(name = "last_session_date")
    private LocalDate lastSessionDate;
}
