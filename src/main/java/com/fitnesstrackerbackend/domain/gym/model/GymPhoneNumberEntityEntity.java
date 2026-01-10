package com.fitnesstrackerbackend.domain.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gym_phone_number")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GymPhoneNumberEntityEntity {
    @NotNull
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @Size(max = 4)
    @NotNull
    @Column(name = "country_prefix", nullable = false, length = 4)
    private String countryPrefix;

    @MapsId("gymid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gymid", nullable = false)
    private GymEntity gymid;

    @EmbeddedId
    private GymPhoneNumberEntityIdEntity id;
}
