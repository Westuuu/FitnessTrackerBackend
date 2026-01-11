package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.domain.trainingplan.dto.ExerciseTemplateCreationDto;
import com.fitnesstrackerbackend.domain.trainingplan.dto.ExerciseTemplateDto;
import com.fitnesstrackerbackend.domain.trainingplan.model.ExerciseTemplateEntity;
import com.fitnesstrackerbackend.domain.trainingplan.repositories.ExerciseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    @Transactional(readOnly = true)
    public List<ExerciseTemplateDto> getExerciseTemplates() {
        List<ExerciseTemplateEntity> exercises = exerciseRepository.findAll();

        return exerciseMapper.mapToExerciseTemplateDtoList(exercises);
    }

    @Transactional
    public ExerciseTemplateDto createExerciseTemplate(ExerciseTemplateCreationDto exerciseTemplateCreationDto) {
        ExerciseTemplateEntity savedExerciseTemplate = exerciseRepository.save(exerciseMapper.mapToExerciseEntity(exerciseTemplateCreationDto));
        return exerciseMapper.mapToExerciseTemplateDto(savedExerciseTemplate);
    }
}
