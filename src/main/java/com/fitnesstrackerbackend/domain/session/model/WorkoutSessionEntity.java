package com.fitnesstrackerbackend.domain.session.model;

import com.fitnesstrackerbackend.domain.trainingplan.model.UserTrainingPlanEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "workout_session", indexes = {
        @Index(name = "idx_workout_session_plan_date",
                columnList = "user_training_planid, session_date"),
        @Index(name = "idx_workout_session_plan_completed",
                columnList = "user_training_planid, completed")})
public class WorkoutSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_training_planid", nullable = false)
    private UserTrainingPlanEntity userTrainingPlanidEntity;

    @NotNull
    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;


}