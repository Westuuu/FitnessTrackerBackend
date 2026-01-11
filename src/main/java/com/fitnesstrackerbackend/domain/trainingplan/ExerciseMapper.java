package com.fitnesstrackerbackend.domain.trainingplan;

import com.fitnesstrackerbackend.domain.trainingplan.dto.ExerciseTemplateCreationDto;
import com.fitnesstrackerbackend.domain.trainingplan.dto.ExerciseTemplateDto;
import com.fitnesstrackerbackend.domain.trainingplan.model.ExerciseTemplateEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class ExerciseMapper {
    public List<ExerciseTemplateDto> mapToExerciseTemplateDtoList(List<ExerciseTemplateEntity> exerciseTemplate) {
        return exerciseTemplate.stream()
                .map(this::mapToExerciseTemplateDto)
                .toList();
    }

    public ExerciseTemplateDto mapToExerciseTemplateDto(ExerciseTemplateEntity exerciseTemplate) {
        return ExerciseTemplateDto.builder()
                .id(exerciseTemplate.getId())
                .name(exerciseTemplate.getName())
                .description(exerciseTemplate.getDescription())
                .instructions(exerciseTemplate.getInstructions())
                .equipmentNeeded(exerciseTemplate.getEquipmentNeeded())
                .muscleGroup(exerciseTemplate.getMuscleGroup())
                .build();
    }

    public ExerciseTemplateEntity mapToExerciseEntity(ExerciseTemplateCreationDto templateCreationDto) {
        return ExerciseTemplateEntity.builder()
                .equipmentNeeded(templateCreationDto.equipmentNeeded())
                .instructions(templateCreationDto.instructions())
                .name(templateCreationDto.name())
                .muscleGroup(templateCreationDto.muscleGroup())
                .description(templateCreationDto.description())
                .createdAt(Instant.now())
                .build();
    }
}
