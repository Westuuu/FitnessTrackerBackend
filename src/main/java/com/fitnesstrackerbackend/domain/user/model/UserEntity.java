package com.fitnesstrackerbackend.domain.user.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column(name = "gymID", nullable = false)
    private Long GymId;

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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TraineeInfoEntity traineeInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TrainerInfoEntity trainerInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AdminInfoEntity adminInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LoginCredentialEntity loginCredential;
}
