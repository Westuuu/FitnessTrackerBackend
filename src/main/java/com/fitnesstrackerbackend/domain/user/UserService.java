package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.auth.dto.UserRegistrationDto;
import com.fitnesstrackerbackend.domain.auth.exception.UserAlreadyExistsException;
import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserProfileDto getUserProfile(Long userID) {
        UserEntity user = userRepository.findByIdWithAllDetails(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userID));

        return mapToUserProfileDto(user);
    }

    private UserProfileDto mapToUserProfileDto(UserEntity user) {
        UserProfileDto.UserProfileDtoBuilder builder = UserProfileDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType())
                .dateOfBirth(user.getDateOfBirth());

        Optional.ofNullable(user.getTraineeInfo())
                .ifPresent(info -> builder
                        .exerciseUnit(info.getExerciseUnit().name())
                        .trainerId(info.getTrainer().getId()));
        Optional.ofNullable(user.getTrainerInfo())
                .ifPresent(info -> builder
                        .specialization(info.getSpecialization())
                        .bio(info.getBio())
                        .isTrainerActive(info.getIsActive()));
        Optional.ofNullable(user.getAdminInfo())
                .ifPresent(info -> builder
                        .isAdminActive(info.getIsActive()));

        return builder.build();
    }
}
