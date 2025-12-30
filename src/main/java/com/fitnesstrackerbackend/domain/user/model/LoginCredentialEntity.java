package com.fitnesstrackerbackend.domain.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "login_credential")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentialEntity {
    @Id
    @Column(name = "userID")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private UserEntity user;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
