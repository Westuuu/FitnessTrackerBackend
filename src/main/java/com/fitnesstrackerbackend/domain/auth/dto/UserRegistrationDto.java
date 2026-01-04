package com.fitnesstrackerbackend.domain.auth.dto;

import com.fitnesstrackerbackend.domain.user.model.Sex;
import com.fitnesstrackerbackend.domain.user.model.UserType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserRegistrationDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private UserType userType;

    @NotNull
    private Long gymId;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotNull
    private Sex sex;
}
