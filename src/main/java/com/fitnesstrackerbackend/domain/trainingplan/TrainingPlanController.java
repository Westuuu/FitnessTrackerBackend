package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.trainingplan.dto.*;
import com.fitnesstrackerbackend.domain.trainingplan.enums.VisibilityType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;

    public TrainingPlanController(TrainingPlanService trainingPlanService) {
        this.trainingPlanService = trainingPlanService;
    }

    @PostMapping("/training-plans")
    public ResponseEntity<TrainingPlanDto> createTrainingPlan(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody TrainingPlanCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trainingPlanService.createTrainingPlan(userDetails.getId(), createDto));
    }

    @GetMapping("/training-plans/my")
    public ResponseEntity<List<UserTrainingPlanDto>> getMyTrainingPlans(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(trainingPlanService.getMyTrainingPlans(userDetails.getId()));
    }

    @GetMapping("/training-plans/created")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<List<TrainingPlanDto>> getCreatedPlans(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(trainingPlanService.getCreatedPlans(userDetails.getId()));
    }

    @PostMapping("/training-plans/{trainingPlanId}/assign")
    public ResponseEntity<UserTrainingPlanDto> assignPlanToUser(
            @PathVariable Long trainingPlanId,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trainingPlanService.assignPlanToUser(userDetails.getId(), trainingPlanId));
    }

    @PostMapping("/training-plans/{trainingPlanId}/assign/{userId}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<UserTrainingPlanDto> assignPlanToTrainee(
            @PathVariable Long trainingPlanId,
            @PathVariable Long userId,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trainingPlanService.assignTrainerPlanToTrainee(userDetails.getId(), userId, trainingPlanId));
    }

    @GetMapping("/training-plans")
    public ResponseEntity<List<TrainingPlanSummaryDto>> getAllTrainingPlans(
            @RequestParam(required = false) VisibilityType visibilityType,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        List<TrainingPlanSummaryDto> plans = trainingPlanService.getAllTrainingPlans(visibilityType,
                userDetails.getId());
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/training-plans/{trainingPlanId}")
    public ResponseEntity<TrainingPlanDto> getTrainingPlanById(
            @PathVariable Long trainingPlanId,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(trainingPlanService.getTrainingPlanById(trainingPlanId, userDetails.getId()));
    }

    // Exercise related endpoints
    @GetMapping("/exercises")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('TRAINEE', 'TRAINER')")
    public ResponseEntity<List<ExerciseTemplateDto>> getExerciseTemplates() {
        return ResponseEntity.ok(trainingPlanService.getExerciseTemplates());
    }

    @PostMapping("/exercises")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<ExerciseTemplateDto> createExerciseTemplate(
            @RequestBody @Valid ExerciseTemplateCreationDto exerciseTemplateCreationDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trainingPlanService.createExerciseTemplate(exerciseTemplateCreationDto));
    }
}
