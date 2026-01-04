package com.fitnesstrackerbackend.domain.session.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "exercise_instance_set", schema = "public", uniqueConstraints = {@UniqueConstraint(name = "exercise_instance_set_unique_per_exercise",
        columnNames = {
                "exercise_instanceid",
                "set_number"})})
public class ExerciseInstanceSetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_instanceid", nullable = false)
    private ExerciseInstanceEntity exerciseInstanceidEntity;

    @NotNull
    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

    @NotNull
    @Column(name = "reps", nullable = false)
    private Integer reps;

    @NotNull
    @Column(name = "weight", nullable = false, precision = 6, scale = 2)
    private BigDecimal weight;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "completed", nullable = false)
    private Boolean completed;


}