package com.fitnesstrackerbackend.domain.user.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TraineeOverviewDto(
        Long traineeId,
        String firstName,
        String lastName,
        String email,
        String activePlanName,
        String membershipStatus,
        LocalDate lastSessionDate
) {
}
