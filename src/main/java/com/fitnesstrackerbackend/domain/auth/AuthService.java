package com.fitnesstrackerbackend.domain.auth;

import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.core.security.JwtService;
import com.fitnesstrackerbackend.domain.auth.dto.LoginRequestDto;
import com.fitnesstrackerbackend.domain.auth.dto.LoginResponseDto;
import com.fitnesstrackerbackend.domain.auth.dto.UserRegistrationDto;
import com.fitnesstrackerbackend.domain.auth.exception.UserAlreadyExistsException;
import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import com.fitnesstrackerbackend.domain.gym.GymRepository;
import com.fitnesstrackerbackend.domain.gym.model.GymEntity;
import com.fitnesstrackerbackend.domain.user.UserRepository;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import jakarta.validation.Valid;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LoginCredentialRepository loginCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GymRepository gymRepository;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Transactional
    public LoginResponseDto register(@Valid UserRegistrationDto registrationDto) {
        if (loginCredentialRepository.existsByEmail(registrationDto.getEmail())) {
            throw new UserAlreadyExistsException(registrationDto.getEmail());
        }

        String passwordHash = passwordEncoder.encode(registrationDto.getPassword());

        GymEntity gym = gymRepository.findById(registrationDto.getGymId())
                .orElseThrow(() -> new ResourceNotFoundException("Gym with id " + registrationDto.getGymId() + " does not exist"));

        UserEntity user = UserEntity.builder()
                .firstName(registrationDto.getFirstName())
                .middleName(registrationDto.getMiddleName())
                .lastName(registrationDto.getLastName())
                .dateOfBirth(registrationDto.getDateOfBirth())
                .sex(registrationDto.getSex())
                .userType(registrationDto.getUserType())
                .gymid(gym)
                .build();

        LoginCredentialEntity loginCredential = new LoginCredentialEntity();
        loginCredential.setEmail(registrationDto.getEmail());
        loginCredential.setPasswordHash(passwordHash);
        loginCredential.setUser(user);

        user.setLoginCredential(loginCredential);

        UserEntity savedUser = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getLoginCredential().getEmail());
        String token = jwtService.generateToken(userDetails);

        return buildLoginResponse(savedUser, token);
    }

    private LoginResponseDto buildLoginResponse(UserEntity user, String token) {
        return LoginResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(user.getLoginCredential().getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userType(user.getUserType())
                .expiresIn(jwtExpiration)
                .build();
    }

    @Transactional
    public LoginResponseDto login(@Valid LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        UserEntity user = userRepository.findByLoginCredentialEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        return buildLoginResponse(user, token);
    }
}
