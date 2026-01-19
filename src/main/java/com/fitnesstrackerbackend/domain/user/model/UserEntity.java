package com.fitnesstrackerbackend.domain.user.model;

import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import com.fitnesstrackerbackend.domain.goal.model.GoalEntity;
import com.fitnesstrackerbackend.domain.gym.model.GymEntity;
import com.fitnesstrackerbackend.domain.trainingplan.model.TrainingPlanRoleEntity;
import com.fitnesstrackerbackend.domain.trainingplan.model.UserTrainingPlanEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gymid", nullable = false)
    private GymEntity gym;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    // @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // private LoginCredentialEntity loginCredential;
    //
    // @OneToOne(mappedBy = "user")
    // private TraineeInfoEntity traineeInfo;
    //
    // @OneToOne(mappedBy = "user")
    // private TrainerInfoEntity trainerInfo;
    //
    // @OneToOne(mappedBy = "user")
    // private AdminInfoEntity adminInfo;

}
