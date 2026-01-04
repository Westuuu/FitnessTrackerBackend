package com.fitnesstrackerbackend.domain.trainingplan.model;

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
@Table(name = "exercise_template", schema = "public", indexes = {
        @Index(name = "idx_exercise_template_name",
                columnList = "name"),
        @Index(name = "idx_exercise_template_muscle_group",
                columnList = "muscle_group")})
public class ExerciseTemplateEntity {
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

    @Size(max = 50)
    @NotNull
    @Column(name = "muscle_group", nullable = false, length = 50)
    private String muscleGroup;

    @Size(max = 255)
    @Column(name = "equipment_needed")
    private String equipmentNeeded;

    @Column(name = "instructions", length = Integer.MAX_VALUE)
    private String instructions;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


}