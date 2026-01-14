package com.fitnesstrackerbackend.domain.goal;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.goal.dto.GoalCreateDto;
import com.fitnesstrackerbackend.domain.goal.dto.GoalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public ResponseEntity<List<GoalDto>> getMyGoals(@AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(goalService.getGoalsByUserId(userDetails.getId()));
    }

    @PostMapping
    public ResponseEntity<GoalDto> createGoal(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody GoalCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(goalService.createGoal(userDetails.getId(), createDto));
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(
            @PathVariable Integer goalId,
            @AuthenticationPrincipal AppUserDetails userDetails) {
        goalService.deleteGoal(goalId, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
