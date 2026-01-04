package com.fitnesstrackerbackend.domain.trainingplan.model;

import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_training_plan", schema = "public", indexes = {@Index(name = "idx_user_training_plan_user_status",
        columnList = "userid, status")}, uniqueConstraints = {@UniqueConstraint(name = "user_training_plan_unique",
        columnNames = {
                "userid",
                "training_planid"})})
public class UserTrainingPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_planid", nullable = false)
    private TrainingPlanEntity trainingPlanidEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private UserEntity userid;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 20)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Size(max = 255)
    @Column(name = "plan_title")
    private String planTitle;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;


}