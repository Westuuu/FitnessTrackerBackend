package com.fitnesstrackerbackend.domain.auth;

import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.core.security.JwtService;
import com.fitnesstrackerbackend.domain.auth.dto.*;
import com.fitnesstrackerbackend.domain.auth.exception.UserAlreadyExistsException;
import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import com.fitnesstrackerbackend.domain.gym.GymRepository;
import com.fitnesstrackerbackend.domain.gym.model.GymEntity;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import com.fitnesstrackerbackend.domain.user.model.UserType;
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
        if (loginCredentialRepository.existsByEmail(registrationDto.email())) {
            throw new UserAlreadyExistsException(registrationDto.email());
        }

        String passwordHash = passwordEncoder.encode(registrationDto.password());

        GymEntity gym = gymRepository.findById(registrationDto.gymId())
                .orElseThrow(() -> new ResourceNotFoundException("Gym with id " + registrationDto.gymId() + " does not exist"));

        UserEntity user = UserEntity.builder()
                .firstName(registrationDto.firstName())
                .middleName(registrationDto.middleName())
                .lastName(registrationDto.lastName())
                .dateOfBirth(registrationDto.dateOfBirth())
                .sex(registrationDto.sex())
                .userType(registrationDto.userType())
                .gym(gym)
                .build();

        LoginCredentialEntity loginCredential = LoginCredentialEntity.builder()
                .email(registrationDto.email())
                .passwordHash(passwordHash)
                .user(user)
                .build();

        UserEntity savedUser = userRepository.save(user);
        LoginCredentialEntity savedCredentials = loginCredentialRepository.save(loginCredential);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedCredentials.getEmail());
        String token = jwtService.generateToken(userDetails);

        return buildLoginResponse(savedUser, savedCredentials, token);
    }

    private LoginResponseDto buildLoginResponse(UserEntity user, LoginCredentialEntity loginCredentials, String token) {
        return LoginResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .email(loginCredentials.getEmail())
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

        LoginCredentialEntity userLoginCredentials = loginCredentialRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        UserEntity user = userLoginCredentials.getUser();
        return buildLoginResponse(user, userLoginCredentials, token);
    }

    @Transactional
    public AdminRegistrationResponseDto registerAdmin(@Valid AdminRegistrationDto registrationDto) {
        if (loginCredentialRepository.existsByEmail(registrationDto.email())) {
            throw new UserAlreadyExistsException(registrationDto.email());
        }

        GymEntity gym = gymRepository.findById(registrationDto.gymId())
                .orElseThrow(() -> new ResourceNotFoundException("Gym with id " + registrationDto.gymId() + " does not exist"));

        UserEntity user = UserEntity.builder()
                .firstName(registrationDto.firstName())
                .middleName(registrationDto.middleName())
                .lastName(registrationDto.lastName())
                .dateOfBirth(registrationDto.dateOfBirth())
                .sex(registrationDto.sex())
                .userType(UserType.ADMIN)
                .gym(gym)
                .build();


        LoginCredentialEntity loginCredential = LoginCredentialEntity.builder()
                .email(registrationDto.email())
                .passwordHash(passwordEncoder.encode(registrationDto.password()))
                .user(user)
                .build();

        userRepository.save(user);
        loginCredentialRepository.save(loginCredential);

        return AdminRegistrationResponseDto.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .email(loginCredential.getEmail())
                .lastName(user.getLastName())
                .userType(user.getUserType())
                .gymId(user.getGym().getId())
                .build();
    }
}
