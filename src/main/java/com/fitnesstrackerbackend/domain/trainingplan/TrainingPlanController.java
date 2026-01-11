package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.trainingplan.dto.TrainingPlanSummaryDto;
import com.fitnesstrackerbackend.domain.trainingplan.enums.VisibilityType;
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

    @GetMapping
    public ResponseEntity<List<TrainingPlanSummaryDto>> getAllTrainingPlans(
            @RequestParam(required = false) VisibilityType visibilityType,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        List<TrainingPlanSummaryDto> plans = trainingPlanService.getAllTrainingPlans(visibilityType, userDetails.getId());
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{trainingPlanId}")
    public ResponseEntity<TrainingPlanSummaryDto> getTrainingPlanById(
            @PathVariable Long trainingPlanId,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return ResponseEntity.ok(trainingPlanService.getTrainingPlanById(trainingPlanId, userDetails.getId()));
    }
//
//    @PostMapping
//    public ResponseEntity<TrainingPlanSummaryDto>
}
