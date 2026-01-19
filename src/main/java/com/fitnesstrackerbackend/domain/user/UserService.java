package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.domain.auth.LoginCredentialRepository;
import com.fitnesstrackerbackend.domain.user.dto.GymUserDto;
import com.fitnesstrackerbackend.domain.user.dto.TraineeOverviewDto;
import com.fitnesstrackerbackend.domain.user.dto.TrainerAssigmentResponseDto;
import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.user.model.MembershipEntity;
import com.fitnesstrackerbackend.domain.user.model.TraineeInfoEntity;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import com.fitnesstrackerbackend.domain.user.model.UserType;
import com.fitnesstrackerbackend.domain.user.repository.MembershipRepository;
import com.fitnesstrackerbackend.domain.user.repository.TraineeInfoRepository;
import com.fitnesstrackerbackend.domain.user.repository.TrainerTraineeViewRepository;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TrainerTraineeViewRepository trainerTraineeViewRepository;
    private final TraineeInfoRepository traineeInfoRepository;
    private final MembershipRepository membershipRepository;
    private final LoginCredentialRepository loginCredentialRepository;

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
        TraineeInfoEntity traineeInfo = traineeInfoRepository.findById(traineeId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + traineeId + " does not exist"));
        UserEntity trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + trainerId + " does not exist"));

        traineeInfo.setTrainer(trainer);

        return new TrainerAssigmentResponseDto(trainerId, traineeId, LocalDateTime.now());

    }

    @Transactional
    public void removeTrainerFromTrainee(Long trainerId, Long traineeId) {
        TraineeInfoEntity traineeInfo = traineeInfoRepository.findById(traineeId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found: " + traineeId));

        // Verify the trainee is actually assigned to this trainer (optional but good
        // for security)
        if (traineeInfo.getTrainer() != null && traineeInfo.getTrainer().getId().equals(trainerId)) {
            traineeInfo.setTrainer(null);
            traineeInfoRepository.save(traineeInfo);
        } else if (traineeInfo.getTrainer() != null) {
            throw new IllegalStateException("Trainee is assigned to a different trainer");
        }
    }

    @Transactional(readOnly = true)
    public List<GymUserDto> getGymUsers(Long gymId) {
        return userRepository.findAllByGymId(gymId).stream()
                .map(user -> {
                    String membershipStatus = "PENDING";
                    boolean isApproved = false;

                    if (user.getUserType() == UserType.TRAINEE) {
                        membershipStatus = membershipRepository.findActiveByTraineeId(user.getId())
                                .map(MembershipEntity::getMembershipStatus)
                                .orElse("PENDING");
                        isApproved = "ACTIVE".equalsIgnoreCase(membershipStatus);
                    } else if (user.getUserType() == UserType.TRAINER) {
                        isApproved = true;
                        membershipStatus = "ACTIVE";
                    }

                    return GymUserDto.builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(getUsername(user))
                            .userType(user.getUserType())
                            .isApproved(isApproved)
                            .membershipStatus(membershipStatus)
                            .gymName(user.getGym() != null ? user.getGym().getName() : "Unknown Gym")
                            .build();
                })
                .toList();
    }

    // Helper to extract email if possible, otherwise null
    private String getUsername(UserEntity user) {
        return loginCredentialRepository.findById(user.getId())
                .map(credential -> credential.getEmail())
                .orElse("N/A");
    }

    @Transactional
    public void approveUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getUserType() != UserType.TRAINEE) {
            return; // Only trainees need approval via membership
        }

        TraineeInfoEntity traineeInfo = traineeInfoRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee info not found"));

        MembershipEntity membership = membershipRepository.findActiveByTraineeId(userId)
                .orElse(new MembershipEntity());

        if (membership.getId() == null) {
            membership.setTraineeInfoid(traineeInfo);
            membership.setMembershipStartDate(LocalDate.now());
        }

        membership.setMembershipStatus("ACTIVE");
        membershipRepository.save(membership);
    }
}
