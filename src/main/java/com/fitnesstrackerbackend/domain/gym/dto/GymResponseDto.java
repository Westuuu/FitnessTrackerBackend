package com.fitnesstrackerbackend.domain.gym.dto;

import java.util.List;

public record GymResponseDto(
        Long id,
        String name,
        String email,
        String country,
        String city,
        String street,
        String streetNumber,
        String apartmentNumber,
        List<String> phoneNumbers

) {
}
