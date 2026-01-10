package com.fitnesstrackerbackend.domain.auth.model;

import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "login_credential")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginCredentialEntity {
    @Id
    @Column(name = "userID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "userID")
    private UserEntity user;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
}
