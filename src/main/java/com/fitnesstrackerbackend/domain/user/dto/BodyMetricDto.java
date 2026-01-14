package com.fitnesstrackerbackend.domain.user.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BodyMetricDto(
        LocalDate date,
        BigDecimal weight,
        Integer height) {
}
