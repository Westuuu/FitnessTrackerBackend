package com.fitnesstrackerbackend.domain.user.dto;

import com.fitnesstrackerbackend.domain.user.model.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GymUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserType userType;
    private Boolean isApproved;
    private String membershipStatus;
    private String gymName;
}
