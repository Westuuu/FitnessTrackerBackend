package com.fitnesstrackerbackend.domain.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "trainer_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerInfoEntity {
    @Id
    @Column(name = "userID")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private UserEntity user;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "bio", columnDefinition = "text")
    private String bio;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
