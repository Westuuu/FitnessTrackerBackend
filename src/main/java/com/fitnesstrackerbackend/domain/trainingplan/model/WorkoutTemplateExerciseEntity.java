package com.fitnesstrackerbackend.domain.trainingplan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "workout_template_exercise", indexes = {@Index(name = "idx_workout_template_exercise_day",
        columnList = "workout_templateid")}, uniqueConstraints = {@UniqueConstraint(name = "workout_template_exercise_unique_order",
        columnNames = {
                "workout_templateid",
                "order_in_workout"})})
public class WorkoutTemplateExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_templateid", nullable = false)
    private WorkoutTemplateDayEntity workoutTemplateid;

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

    @NotNull
    @Column(name = "planned_weight", nullable = false, precision = 6, scale = 2)
    private BigDecimal plannedWeight;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;


}