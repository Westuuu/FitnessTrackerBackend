package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.database.DatabaseContextHolder;
import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.domain.user.dto.TraineeOverviewDto;
import com.fitnesstrackerbackend.domain.user.dto.TrainerAssigmentResponseDto;
import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.user.model.TraineeInfoEntity;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import com.fitnesstrackerbackend.domain.user.model.UserType;
import com.fitnesstrackerbackend.domain.user.repository.TraineeInfoRepository;
import com.fitnesstrackerbackend.domain.user.repository.TrainerTraineeViewRepository;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TrainerTraineeViewRepository trainerTraineeViewRepository;
    private final TraineeInfoRepository traineeInfoRepository;

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Long userID) {
        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userID));

        return userMapper.mapToUserProfileDto(user, user.getUserType());
    }

    @Transactional(readOnly = true)
    public UserProfileDto getCurrentUserProfile(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userMapper.mapToUserProfileDto(user, user.getUserType());
    }

    @Transactional(readOnly = true)
    public List<TraineeOverviewDto> getTrainerTrainees(Long id) {
        return trainerTraineeViewRepository.findByTrainerId(id)
                .stream()
                .map(userMapper::mapToTraineeOverviewDto)
                .toList();
    }

    @Transactional
    public TrainerAssigmentResponseDto assignTrainerToTrainee(Long trainerId, Long traineeId) {
        Optional<TraineeInfoEntity> traineeInfo = traineeInfoRepository.findById(traineeId);
        UserEntity trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + trainerId + " does not exist"));
        traineeInfo.ifPresent(
                traineeInfoEntity -> traineeInfoEntity.setTrainer(trainer)
        );
        return new TrainerAssigmentResponseDto(trainerId, traineeId, LocalDateTime.now());

    }
}
