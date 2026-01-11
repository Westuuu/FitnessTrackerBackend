package com.fitnesstrackerbackend.domain.trainingplan.model;

import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "training_plan_role", indexes = {@Index(name = "idx_training_plan_role_user",
        columnList = "userid")}, uniqueConstraints = {@UniqueConstraint(name = "training_plan_role_unique",
        columnNames = {
                "training_planid",
                "userid"})})
public class TrainingPlanRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_planid", nullable = false)
    private TrainingPlanEntity trainingPlan;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private UserEntity userid;

    @Size(max = 20)
    @NotNull
    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "granted_at", nullable = false)
    private Instant grantedAt;


}