package com.fitnesstrackerbackend.domain.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "gym")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GymEntity {
    @Builder.Default
    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "gym", cascade = CascadeType.ALL)
    private AddressEntity address;

    @Builder.Default
    @OneToMany(mappedBy = "gymid", cascade = CascadeType.ALL)
    private Set<GymPhoneNumberEntityEntity> gymPhoneNumbers = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "gymid", cascade = CascadeType.ALL)
    private Set<OpeningHoursEntity> openingHours = new LinkedHashSet<>();
}
