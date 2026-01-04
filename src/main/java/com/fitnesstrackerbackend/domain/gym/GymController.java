package com.fitnesstrackerbackend.domain.gym;


import com.fitnesstrackerbackend.domain.gym.dto.GymCreationRequestDto;
import com.fitnesstrackerbackend.domain.gym.dto.GymCreationResponseDto;
import com.fitnesstrackerbackend.domain.gym.dto.GymResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gym")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class GymController {

    private final GymService gymService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<GymCreationResponseDto> createGym(
            @RequestBody @Valid GymCreationRequestDto gymCreationRequestDto
    ) {
        GymCreationResponseDto gymCreationResponseDto = gymService.createGym(gymCreationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(gymCreationResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<GymResponseDto>> getAllGyms() {
        return ResponseEntity.ok(gymService.getAllGyms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymResponseDto> getGym(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(gymService.findById(id));
    }
}
