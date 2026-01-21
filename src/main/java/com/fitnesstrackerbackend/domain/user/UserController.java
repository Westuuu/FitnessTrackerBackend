package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.auth.AuthService;
import com.fitnesstrackerbackend.domain.auth.dto.TrainerRegistrationDto;
import com.fitnesstrackerbackend.domain.auth.dto.TrainerRegistrationResponseDto;
import com.fitnesstrackerbackend.domain.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/users/{userID}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #userID")
    public ResponseEntity<UserProfileDto> getUserProfile(
            @PathVariable Long userID) {
        return ResponseEntity.ok(userService.getUserProfile(userID));
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserProfileDto> getProfile(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(userService.getCurrentUserProfile(userDetails.getId()));
    }

    @GetMapping("/users/trainees")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<TraineeOverviewDto>> getTrainerTrainees(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(userService.getTrainerTrainees(userDetails.getId()));
    }

    @PutMapping("/users/{trainerId}/trainees")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<TrainerAssigmentResponseDto> assignTrainerToTrainee(
            @PathVariable Long trainerId,
            @RequestBody com.fitnesstrackerbackend.domain.user.dto.AssignTraineeRequest request) {
        return ResponseEntity.ok(userService.assignTrainerToTrainee(trainerId, request.traineeId()));
    }

    @DeleteMapping("/users/{trainerId}/trainees/{traineeId}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<Void> removeTrainerFromTrainee(
            @PathVariable Long trainerId,
            @PathVariable Long traineeId) {
        userService.removeTrainerFromTrainee(trainerId, traineeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/gym/{gymId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    public ResponseEntity<List<com.fitnesstrackerbackend.domain.user.dto.GymUserDto>> getGymUsers(
            @PathVariable Long gymId) {
        return ResponseEntity.ok(userService.getGymUsers(gymId));
    }

    @PostMapping("/users/{userId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approveUser(@PathVariable Long userId) {
        userService.approveUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/trainers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrainerRegistrationResponseDto> registerTrainer(
            @AuthenticationPrincipal AppUserDetails adminDetails,
            @RequestBody TrainerRegistrationDto registrationDto) {
        // We use the admin's gymId as the context for the new trainer
        UserProfileDto adminProfile = userService.getUserProfile(adminDetails.getId());
        return ResponseEntity.status(201).body(authService.registerTrainer(registrationDto, adminProfile.getGymId()));
    }

    // Body Metric related endpoints
    @GetMapping("/me/metrics")
    public ResponseEntity<List<BodyMetricDto>> getMyMetrics(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(userService.getBodyMetricsByUserId(userDetails.getId()));
    }

    @PostMapping("/me/metrics")
    public ResponseEntity<BodyMetricDto> saveMyMetric(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody BodyMetricDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.saveMetric(userDetails.getId(), dto));
    }
}
