package com.fitnesstrackerbackend.domain.gym;

import com.fitnesstrackerbackend.domain.gym.dto.GymCreationRequestDto;
import com.fitnesstrackerbackend.domain.gym.dto.GymCreationResponseDto;
import com.fitnesstrackerbackend.domain.gym.dto.GymResponseDto;
import com.fitnesstrackerbackend.domain.gym.model.AddressEntity;
import com.fitnesstrackerbackend.domain.gym.model.GymEntity;
import org.springframework.stereotype.Component;

@Component
class GymMapper {
    public GymEntity mapToGymEntity(GymCreationRequestDto gymCreationRequestDto) {
        GymEntity gym = GymEntity.builder()
                .name(gymCreationRequestDto.name())
                .email(gymCreationRequestDto.email())
                .build();

        AddressEntity address = AddressEntity.builder()
                .country(gymCreationRequestDto.country())
                .city(gymCreationRequestDto.city())
                .postCode(gymCreationRequestDto.postCode())
                .street(gymCreationRequestDto.street())
                .streetNumber(gymCreationRequestDto.streetNumber())
                .apartmentNumber(
                        gymCreationRequestDto.apartmentNumber() != null ? gymCreationRequestDto.apartmentNumber() : "")
                .gym(gym)
                .build();

        gym.setAddress(address);
        return gym;
    }

    public GymCreationResponseDto mapToGymCreationResponseDto(GymEntity savedGym) {
        return new GymCreationResponseDto(
                savedGym.getId(),
                savedGym.getName(),
                savedGym.getEmail(),
                savedGym.getCreatedAt());
    }

    public GymResponseDto mapToGymResponseDto(GymEntity gym) {
        AddressEntity address = gym.getAddress();
        return new GymResponseDto(
                gym.getId(),
                gym.getName(),
                gym.getEmail(),
                address != null ? address.getCountry() : null,
                address != null ? address.getCity() : null,
                address != null ? address.getStreet() : null,
                address != null ? address.getStreetNumber() : null,
                address != null ? address.getApartmentNumber() : null,
                gym.getGymPhoneNumbers().stream()
                        .map(phone -> phone.getCountryPrefix() + phone.getId().getPhoneNumber())
                        .toList());

    }
}
