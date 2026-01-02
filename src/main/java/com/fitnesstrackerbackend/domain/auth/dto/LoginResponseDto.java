package com.fitnesstrackerbackend.domain.auth.dto;

import com.fitnesstrackerbackend.domain.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String token;

    private String tokenType;

    private Long userId;

    private String email;

    private String firstName;

    private String lastName;

    private UserType userType;

    private Long expiresIn;
}
