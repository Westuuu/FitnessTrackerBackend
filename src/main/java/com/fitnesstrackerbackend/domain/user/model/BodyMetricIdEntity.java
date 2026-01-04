package com.fitnesstrackerbackend.domain.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class BodyMetricIdEntity implements Serializable {
    private static final long serialVersionUID = -9158013680460981761L;
    @NotNull
    @Column(name = "userid", nullable = false)
    private Long userid;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;


}