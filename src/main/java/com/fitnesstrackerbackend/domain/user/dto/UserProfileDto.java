package com.fitnesstrackerbackend.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fitnesstrackerbackend.domain.user.model.UserType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private UserType userType;
    private LocalDate dateOfBirth;

    // Specific to trainee - null if userType != trainee
    private String exerciseUnit;
    private Long trainerId;

    // Specific to trainer - null if userType != trainer
    private String specialization;
    private String bio;
    private Boolean isTrainerActive;

    // Specific to admin - null if userType != admin
    private Boolean isAdminActive;

}
