package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.domain.user.dto.BodyMetricDto;
import com.fitnesstrackerbackend.domain.user.model.BodyMetricEntity;
import com.fitnesstrackerbackend.domain.user.repository.BodyMetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BodyMetricService {

    private final BodyMetricRepository bodyMetricRepository;

    @Transactional(readOnly = true)
    public List<BodyMetricDto> getBodyMetricsByUserId(Long userId) {
        return bodyMetricRepository.findById_UseridOrderById_DateAsc(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private BodyMetricDto mapToDto(BodyMetricEntity entity) {
        return new BodyMetricDto(
                entity.getId().getDate(),
                entity.getWeight(),
                entity.getHeight());
    }
}
