package com.fitnesstrackerbackend.domain.auth;

import com.fitnesstrackerbackend.core.exception.ResourceNotFoundException;
import com.fitnesstrackerbackend.core.security.JwtService;
import com.fitnesstrackerbackend.domain.auth.dto.*;
import com.fitnesstrackerbackend.domain.auth.exception.UserAlreadyExistsException;
import com.fitnesstrackerbackend.domain.auth.model.LoginCredentialEntity;
import com.fitnesstrackerbackend.domain.gym.GymRepository;
import com.fitnesstrackerbackend.domain.gym.model.GymEntity;
import com.fitnesstrackerbackend.domain.user.model.UserEntity;
import com.fitnesstrackerbackend.domain.user.model.UserType;
import com.fitnesstrackerbackend.domain.user.model.TrainerInfoEntity;
import com.fitnesstrackerbackend.domain.user.repository.MembershipRepository;
import com.fitnesstrackerbackend.domain.user.repository.TrainerInfoRepository;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
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

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@lombok.extern.slf4j.Slf4j
public class AuthService {

        private final UserRepository userRepository;
        private final LoginCredentialRepository loginCredentialRepository;
        private final PasswordEncoder passwordEncoder;
        private final UserDetailsService userDetailsService;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final GymRepository gymRepository;
        private final MembershipRepository membershipRepository;
        private final TrainerInfoRepository trainerInfoRepository;

        @Value("${jwt.expiration}")
        private Long jwtExpiration;

        @Transactional
        public LoginResponseDto register(@Valid UserRegistrationDto registrationDto) {
                if (loginCredentialRepository.existsByEmail(registrationDto.email())) {
                        throw new UserAlreadyExistsException(registrationDto.email());
                }

                String passwordHash = passwordEncoder.encode(registrationDto.password());

                GymEntity gym = gymRepository.findById(registrationDto.gymId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Gym with id " + registrationDto.gymId() + " does not exist"));

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

        private LoginResponseDto buildLoginResponse(UserEntity user, LoginCredentialEntity loginCredentials,
                        String token) {
                boolean isApproved = true;
                if (user.getUserType() == UserType.TRAINEE) {
                        isApproved = membershipRepository.findActiveByTraineeId(user.getId()).isPresent();
                }

                return LoginResponseDto.builder()
                                .token(token)
                                .tokenType("Bearer")
                                .userId(user.getId())
                                .email(loginCredentials.getEmail())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .userType(user.getUserType())
                                .gymId(user.getGym() != null ? user.getGym().getId() : null)
                                .isApproved(isApproved)
                                .expiresIn(jwtExpiration)
                                .build();
        }

        @Transactional
        public LoginResponseDto login(@Valid LoginRequestDto loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getEmail(),
                                                loginRequest.getPassword()));

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtService.generateToken(userDetails);

                LoginCredentialEntity userLoginCredentials = loginCredentialRepository
                                .findByEmail(loginRequest.getEmail())
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
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Gym with id " + registrationDto.gymId() + " does not exist"));

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

        @Transactional
        public TrainerRegistrationResponseDto registerTrainer(@Valid TrainerRegistrationDto registrationDto,
                        Long adminGymId) {
                log.info("Registering trainer: {} with gymId: {}", registrationDto.email(), adminGymId);
                try {
                        if (loginCredentialRepository.existsByEmail(registrationDto.email())) {
                                log.warn("Trainer registration failed: Email {} already exists",
                                                registrationDto.email());
                                throw new UserAlreadyExistsException(registrationDto.email());
                        }

                        GymEntity gym = gymRepository.findById(adminGymId)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Gym with id " + adminGymId + " does not exist"));

                        UserEntity user = UserEntity.builder()
                                        .firstName(registrationDto.firstName())
                                        .middleName(registrationDto.middleName())
                                        .lastName(registrationDto.lastName())
                                        .dateOfBirth(registrationDto.dateOfBirth())
                                        .sex(registrationDto.sex())
                                        .userType(UserType.TRAINER)
                                        .gym(gym)
                                        .build();

                        LoginCredentialEntity loginCredential = LoginCredentialEntity.builder()
                                        .email(registrationDto.email())
                                        .passwordHash(passwordEncoder.encode(registrationDto.password()))
                                        .user(user)
                                        .build();

                        log.debug("Saving user and credentials for trainer: {}", registrationDto.email());
                        userRepository.save(user);
                        loginCredentialRepository.save(loginCredential);

                        // Initialize TrainerInfo
                        log.debug("Initializing TrainerInfo for trainerId: {}", user.getId());
                        if (trainerInfoRepository.existsById(user.getId())) {
                                log.warn("TrainerInfo already exists for user {}. Deleting orphan record.",
                                                user.getId());
                                trainerInfoRepository.deleteById(user.getId());
                                trainerInfoRepository.flush();
                        }

                        TrainerInfoEntity trainerInfo = new TrainerInfoEntity();
                        trainerInfo.setUser(user);
                        trainerInfo.setHireDate(LocalDate.now());
                        trainerInfo.setIsActive(true);
                        trainerInfoRepository.save(trainerInfo);

                        log.info("Trainer registered successfully: {}", registrationDto.email());
                        return TrainerRegistrationResponseDto.builder()
                                        .userId(user.getId())
                                        .firstName(user.getFirstName())
                                        .lastName(user.getLastName())
                                        .email(loginCredential.getEmail())
                                        .userType(user.getUserType())
                                        .build();
                } catch (Exception e) {
                        log.error("Error during trainer registration for email: " + registrationDto.email(), e);
                        throw e;
                }
        }
}
