package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.user.dto.TraineeOverviewDto;
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
            @PathVariable Long userID
    ) {
        return ResponseEntity.ok(userService.getUserProfile(userID));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return ResponseEntity.ok(userService.getCurrentUserProfile(userDetails.getId()));
    }

    @GetMapping("/trainees")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<TraineeOverviewDto>> getTrainerTrainees(
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return ResponseEntity.ok(userService.getTrainerTrainees(userDetails.getId()));
    }
}
