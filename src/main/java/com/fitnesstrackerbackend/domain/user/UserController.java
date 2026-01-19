package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.user.dto.TraineeOverviewDto;
import com.fitnesstrackerbackend.domain.user.dto.TrainerAssigmentResponseDto;
import com.fitnesstrackerbackend.domain.user.dto.UserProfileDto;
import com.fitnesstrackerbackend.domain.user.repository.TrainerTraineeViewRepository;
import com.fitnesstrackerbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userID}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #userID")
    public ResponseEntity<UserProfileDto> getUserProfile(
            @PathVariable Long userID) {
        return ResponseEntity.ok(userService.getUserProfile(userID));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(userService.getCurrentUserProfile(userDetails.getId()));
    }

    @GetMapping("/trainees")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<TraineeOverviewDto>> getTrainerTrainees(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(userService.getTrainerTrainees(userDetails.getId()));
    }

    @PutMapping("/{trainerId}/trainees")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<TrainerAssigmentResponseDto> assignTrainerToTrainee(
            @PathVariable Long trainerId,
            @RequestBody com.fitnesstrackerbackend.domain.user.dto.AssignTraineeRequest request) {
        return ResponseEntity.ok(userService.assignTrainerToTrainee(trainerId, request.traineeId()));
    }

    @GetMapping("/gym/{gymId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER')")
    public ResponseEntity<List<com.fitnesstrackerbackend.domain.user.dto.GymUserDto>> getGymUsers(
            @PathVariable Long gymId) {
        return ResponseEntity.ok(userService.getGymUsers(gymId));
    }

    @PostMapping("/{userId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approveUser(@PathVariable Long userId) {
        userService.approveUser(userId);
        return ResponseEntity.ok().build();
    }

}
