package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.domain.user.dto.TraineeOverviewDto;
import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.user.model.TrainerTraineeView;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import com.fitnesstrackerbackend.domain.user.model.UserType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {
        public UserProfileDto mapToUserProfileDto(UserEntity user, UserType currentRole) {
                UserProfileDto.UserProfileDtoBuilder builder = UserProfileDto.builder()
                                .id(user.getId())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .userType(user.getUserType())
                                .gymId(user.getGym() != null ? user.getGym().getId() : null)
                                .dateOfBirth(user.getDateOfBirth());

                return builder.build();
        }

        public TraineeOverviewDto mapToTraineeOverviewDto(TrainerTraineeView view) {
                return TraineeOverviewDto.builder()
                                .traineeId(view.getTraineeId())
                                .firstName(view.getFirstName())
                                .lastName(view.getLastName())
                                .email(view.getEmail())
                                .activePlanName(view.getActivePlanName())
                                .membershipStatus(view.getMembershipStatus())
                                .lastSessionDate(view.getLastSessionDate())
                                .completedSessions(view.getCompletedSessions())
                                .planDurationWeeks(view.getPlanDurationWeeks())
                                .build();
        }
}
