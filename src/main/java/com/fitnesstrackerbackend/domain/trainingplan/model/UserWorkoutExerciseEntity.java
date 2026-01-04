package com.fitnesstrackerbackend.domain.trainingplan.model;

import com.fitnesstrackerbackend.domain.session.model.WorkoutSessionEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "user_workout_exercise", schema = "public", indexes = {@Index(name = "idx_user_workout_exercise_session",
        columnList = "workout_sessionid")}, uniqueConstraints = {@UniqueConstraint(name = "user_workout_exercise_unique_order",
        columnNames = {
                "workout_sessionid",
                "order_in_workout"})})
public class UserWorkoutExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_sessionid", nullable = false)
    private WorkoutSessionEntity workoutSessionidEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_templateid", nullable = false)
    private ExerciseTemplateEntity exerciseTemplateidEntity;

    @NotNull
    @Column(name = "order_in_workout", nullable = false)
    private Integer orderInWorkout;

    @NotNull
    @Column(name = "planned_sets", nullable = false)
    private Integer plannedSets;

    @NotNull
    @Column(name = "planned_reps", nullable = false)
    private Integer plannedReps;

    @Column(name = "planned_weight", precision = 6, scale = 2)
    private BigDecimal plannedWeight;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;


}