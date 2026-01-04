package com.fitnesstrackerbackend.domain.user.model;


import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import com.fitnesstrackerbackend.domain.gym.model.GymEntity;
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gymid", nullable = false)
    private GymEntity gymid;

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

    @Builder.Default
    @OneToMany(mappedBy = "userid", cascade = CascadeType.ALL)
    private Set<UserPhoneNumberEntity> userPhoneNumbers = new LinkedHashSet<>();

}
