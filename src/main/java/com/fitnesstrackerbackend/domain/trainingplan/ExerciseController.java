package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.domain.trainingplan.dto.ExerciseTemplateCreationDto;
import com.fitnesstrackerbackend.domain.trainingplan.dto.ExerciseTemplateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TRAINEE', 'TRAINER')")
    public ResponseEntity<List<ExerciseTemplateDto>> getExerciseTemplates() {
        return ResponseEntity.ok(exerciseService.getExerciseTemplates());
    }

    @PostMapping
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<ExerciseTemplateDto> createExerciseTemplate(
            @RequestBody @Valid ExerciseTemplateCreationDto exerciseTemplateCreationDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseService.createExerciseTemplate(exerciseTemplateCreationDto));
    }
}
