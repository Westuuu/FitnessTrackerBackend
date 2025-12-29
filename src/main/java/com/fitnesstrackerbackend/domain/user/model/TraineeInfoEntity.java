package com.fitnesstrackerbackend.domain.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trainee_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeInfoEntity {
    @Id
    @Column(name = "userID")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_userID")
    private UserEntity trainer;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_unit", nullable = false)
    private ExerciseUnit exerciseUnit;
}
