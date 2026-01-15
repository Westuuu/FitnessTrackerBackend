package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.trainingplan.dto.TrainingPlanDto;
import com.fitnesstrackerbackend.domain.trainingplan.dto.TrainingPlanCreateDto;
import com.fitnesstrackerbackend.domain.trainingplan.dto.TrainingPlanSummaryDto;
import com.fitnesstrackerbackend.domain.trainingplan.dto.UserTrainingPlanDto;
import com.fitnesstrackerbackend.domain.trainingplan.enums.VisibilityType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-plans")
public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;

    public TrainingPlanController(TrainingPlanService trainingPlanService) {
        this.trainingPlanService = trainingPlanService;
    }

    @PostMapping
    public ResponseEntity<TrainingPlanDto> createTrainingPlan(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody TrainingPlanCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trainingPlanService.createTrainingPlan(userDetails.getId(), createDto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<UserTrainingPlanDto>> getMyTrainingPlans(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(trainingPlanService.getMyTrainingPlans(userDetails.getId()));
    }

    @PostMapping("/{trainingPlanId}/assign")
    public ResponseEntity<UserTrainingPlanDto> assignPlanToUser(
            @PathVariable Long trainingPlanId,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trainingPlanService.assignPlanToUser(userDetails.getId(), trainingPlanId));
    }

    @GetMapping
    public ResponseEntity<List<TrainingPlanSummaryDto>> getAllTrainingPlans(
            @RequestParam(required = false) VisibilityType visibilityType,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        List<TrainingPlanSummaryDto> plans = trainingPlanService.getAllTrainingPlans(visibilityType,
                userDetails.getId());
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{trainingPlanId}")
    public ResponseEntity<TrainingPlanDto> getTrainingPlanById(
            @PathVariable Long trainingPlanId,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(trainingPlanService.getTrainingPlanById(trainingPlanId, userDetails.getId()));
    }
}
