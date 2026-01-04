package com.fitnesstrackerbackend.domain.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "body_metrics")
public class BodyMetricEntity {
    @EmbeddedId
    private BodyMetricIdEntity id;

    @MapsId("userid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private TraineeInfoEntity userid;

    @NotNull
    @Column(name = "weight", nullable = false, precision = 6, scale = 2)
    private BigDecimal weight;

    @NotNull
    @Column(name = "height", nullable = false)
    private Integer height;


}