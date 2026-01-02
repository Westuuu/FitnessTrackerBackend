package com.fitnesstrackerbackend.domain.auth;

import com.fitnesstrackerbackend.domain.auth.dto.LoginRequestDto;
import com.fitnesstrackerbackend.domain.auth.dto.LoginResponseDto;
import com.fitnesstrackerbackend.domain.auth.dto.UserRegistrationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDto> register(
            @RequestBody @Valid UserRegistrationDto registrationDto
    ) {
        LoginResponseDto responseDto = authService.register(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody @Valid LoginRequestDto loginRequestDto
    ) {
        LoginResponseDto responseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
