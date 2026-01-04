package com.fitnesstrackerbackend.domain.gym;

import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.domain.gym.dto.GymCreationRequestDto;
import com.fitnesstrackerbackend.domain.gym.dto.GymCreationResponseDto;
import com.fitnesstrackerbackend.domain.gym.dto.GymResponseDto;
import com.fitnesstrackerbackend.domain.gym.model.GymEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GymService {

    private final GymMapper gymMapper;
    private final GymRepository gymRepository;

    @Transactional
    public GymCreationResponseDto createGym(GymCreationRequestDto gymCreationRequestDto) {
        GymEntity gym = gymMapper.mapToGymEntity(gymCreationRequestDto);

        GymEntity savedGym = gymRepository.save(gym);

        return gymMapper.mapToGymCreationResponseDto(savedGym);
    }


    @Transactional(readOnly = true)
    public List<GymResponseDto> getAllGyms() {
        return gymRepository.findAll()
                .stream()
                .map(gymMapper::mapToGymResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GymResponseDto findById(Long id) {
        return gymRepository.findById(id)
                .map(gymMapper::mapToGymResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Gym not found with id: " + id));
    }
}
