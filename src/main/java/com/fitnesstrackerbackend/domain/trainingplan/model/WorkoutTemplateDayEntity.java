package com.fitnesstrackerbackend.domain.trainingplan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workout_template_day", indexes = {@Index(name = "idx_workout_template_day_plan",
        columnList = "training_planid")})
public class WorkoutTemplateDayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_planid", nullable = false)
    private TrainingPlanEntity trainingPlanidEntity;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "notes", length = Integer.MAX_VALUE)
    private String notes;


}