package com.fitnesstrackerbackend.domain.gym.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "opening_hours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHoursEntity {
    @NotNull
    @Column(name = "closes_at", nullable = false)
    private LocalTime closesAt;
    @NotNull
    @Column(name = "opens_at", nullable = false)
    private LocalTime opensAt;
    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "gymid", nullable = false)
    private GymEntity gymid;
    @EmbeddedId
    private OpeningHoursIdEntity id;
}
