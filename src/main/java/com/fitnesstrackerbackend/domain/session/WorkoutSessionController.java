package com.fitnesstrackerbackend.domain.session;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.session.dto.ExerciseProgressDto;
import com.fitnesstrackerbackend.domain.session.dto.WorkoutSessionCreateDto;
import com.fitnesstrackerbackend.domain.session.dto.WorkoutSessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    @GetMapping("/active")
    public ResponseEntity<WorkoutSessionDto> getActiveSession(@AuthenticationPrincipal AppUserDetails userDetails) {
        WorkoutSessionDto session = workoutSessionService.getActiveSession(userDetails.getId());
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(session);
    }

    @PostMapping("/start")
    public ResponseEntity<WorkoutSessionDto> startSession(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestParam Long userTrainingPlanId,
            @RequestParam Long workoutTemplateId) {
        return ResponseEntity
                .ok(workoutSessionService.startSession(userDetails.getId(), userTrainingPlanId, workoutTemplateId));
    }

    @PatchMapping("/{sessionId}/finish")
    public ResponseEntity<Void> finishSession(
            @PathVariable Long sessionId,
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody(required = false) String notes) {
        workoutSessionService.finishSession(sessionId, userDetails.getId(), notes);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/sets/{setId}")
    public ResponseEntity<Void> updateSet(
            @PathVariable Long setId,
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody UpdateSetRequest request) {
        workoutSessionService.updateSet(setId, userDetails.getId(), request.reps(), request.weight(),
                request.completed());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history")
    public ResponseEntity<java.util.List<WorkoutSessionDto>> getSessionHistory(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(workoutSessionService.getSessionHistory(userDetails.getId()));
    }

    @GetMapping("/progress/{exerciseName}")
    public ResponseEntity<java.util.List<ExerciseProgressDto>> getExerciseProgress(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @PathVariable String exerciseName) {
        return ResponseEntity.ok(workoutSessionService.getExerciseProgress(userDetails.getId(), exerciseName));
    }

    @PostMapping
    public ResponseEntity<Void> createSession(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody WorkoutSessionCreateDto createDto) {
        workoutSessionService.saveSession(userDetails.getId(), createDto);
        return ResponseEntity.ok().build();
    }

    public record UpdateSetRequest(Integer reps, java.math.BigDecimal weight, Boolean completed) {
    }
}
