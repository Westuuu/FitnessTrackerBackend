package com.fitnesstrackerbackend.domain.gym.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class OpeningHoursIdEntity implements Serializable {
    private static final long serialVersionUID = 6931135964499613190L;
    @NotNull
    @Column(name = "gymid", nullable = false)
    private Integer gymid;

    @NotNull
    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;


}