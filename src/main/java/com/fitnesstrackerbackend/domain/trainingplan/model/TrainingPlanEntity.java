package com.fitnesstrackerbackend.domain.trainingplan.model;

import com.fitnesstrackerbackend.domain.trainingplan.enums.DifficultyLevel;
import com.fitnesstrackerbackend.domain.trainingplan.enums.VisibilityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "training_plan")
public class TrainingPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @Column(name = "difficulty_level", length = 20)
    private DifficultyLevel difficultyLevel;

    @NotNull
    @Column(name = "duration_weeks", nullable = false)
    private Integer durationWeeks;

    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @NotNull
    @Column(name = "visibility_type", nullable = false, length = 20)
    private VisibilityType visibilityType;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "trainingPlanidEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<WorkoutTemplateDayEntity> workoutDays = new java.util.ArrayList<>();
}