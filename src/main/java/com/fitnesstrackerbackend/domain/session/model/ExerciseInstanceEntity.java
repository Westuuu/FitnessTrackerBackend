package com.fitnesstrackerbackend.domain.session.model;

import com.fitnesstrackerbackend.domain.trainingplan.model.UserWorkoutExerciseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "exercise_instance", indexes = {@Index(name = "idx_exercise_instance_workout_exercise",
        columnList = "user_workout_exerciseid")})
public class ExerciseInstanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_workout_exerciseid", nullable = false)
    private UserWorkoutExerciseEntity userWorkoutExerciseidEntity;

    @NotNull
    @Column(name = "order_in_workout", nullable = false)
    private Integer orderInWorkout;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "completed", nullable = false)
    private Boolean completed;


}