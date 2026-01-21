package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.domain.user.dto.BodyMetricDto;
import com.fitnesstrackerbackend.domain.user.model.BodyMetricEntity;
import com.fitnesstrackerbackend.domain.user.model.BodyMetricIdEntity;
import com.fitnesstrackerbackend.domain.user.model.TraineeInfoEntity;
import com.fitnesstrackerbackend.domain.user.repository.BodyMetricRepository;
import com.fitnesstrackerbackend.domain.user.repository.TraineeInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@lombok.extern.slf4j.Slf4j
public class BodyMetricService {

    private final BodyMetricRepository bodyMetricRepository;
    private final TraineeInfoRepository traineeInfoRepository;

    @Transactional(readOnly = true)
    public List<BodyMetricDto> getBodyMetricsByUserId(Long userId) {
        return bodyMetricRepository.findById_UseridOrderById_DateAsc(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BodyMetricDto saveMetric(Long userId, BodyMetricDto dto) {
        log.info("Saving body metric for userId={}, weight={}, height={}, date={}",
                userId, dto.weight(), dto.height(), dto.date());

        TraineeInfoEntity trainee = traineeInfoRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Trainee not found for userId: " + userId));

        BodyMetricIdEntity id = new BodyMetricIdEntity();
        id.setUserid(userId);
        id.setDate(dto.date() != null ? dto.date() : LocalDate.now());

        BodyMetricEntity entity = bodyMetricRepository.findById(id)
                .orElse(new BodyMetricEntity());

        entity.setId(id);
        entity.setUserid(trainee);
        entity.setWeight(dto.weight());
        entity.setHeight(dto.height());

        BodyMetricEntity saved = bodyMetricRepository.save(entity);
        log.info("Successfully saved body metric with id={}, date={}", saved.getId().getUserid(),
                saved.getId().getDate());

        return mapToDto(saved);
    }

    private BodyMetricDto mapToDto(BodyMetricEntity entity) {
        return new BodyMetricDto(
                entity.getId().getDate(),
                entity.getWeight(),
                entity.getHeight());
    }
}
