package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.database.DbRole;
import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapper {
    public UserProfileDto mapToUserProfileDto(UserEntity user, DbRole currentRole) {
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

        if (currentRole == DbRole.ADMIN) {
            Optional.ofNullable(user.getAdminInfo())
                    .ifPresent(info -> builder.isAdminActive(info.getIsActive()));
        }

        return builder.build();
    }
}
