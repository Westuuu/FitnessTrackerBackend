package com.fitnesstrackerbackend.domain.auth.dto;

import com.fitnesstrackerbackend.domain.user.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record LoginResponseDto(
                String token,

                String tokenType,

                Long userId,

                String email,

                String firstName,

                String lastName,

                UserType userType,
                Long gymId,
                boolean isApproved,
                Long expiresIn) {
}
