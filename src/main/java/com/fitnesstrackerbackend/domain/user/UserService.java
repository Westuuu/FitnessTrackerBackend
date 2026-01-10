package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.database.DatabaseContextHolder;
import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.domain.user.dto.TraineeOverviewDto;
import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import com.fitnesstrackerbackend.domain.user.model.UserType;
import com.fitnesstrackerbackend.domain.user.repository.TrainerTraineeViewRepository;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TrainerTraineeViewRepository trainerTraineeViewRepository;

    public UserProfileDto getUserProfile(Long userID) {
        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userID));

        return userMapper.mapToUserProfileDto(user, user.getUserType());
    }

    public UserProfileDto getCurrentUserProfile(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userMapper.mapToUserProfileDto(user, user.getUserType());
    }

    public List<TraineeOverviewDto> getTrainerTrainees(Long id) {
        return trainerTraineeViewRepository.findByTrainerId(id)
                .stream()
                .map(userMapper::mapToTraineeOverviewDto)
                .toList();
    }
}
