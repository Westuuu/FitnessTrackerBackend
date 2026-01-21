package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.core.security.AppUserDetails;
import com.fitnesstrackerbackend.domain.user.dto.BodyMetricDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me/metrics")
@RequiredArgsConstructor
public class BodyMetricController {

    private final BodyMetricService bodyMetricService;

    @GetMapping
    public ResponseEntity<List<BodyMetricDto>> getMyMetrics(
            @AuthenticationPrincipal AppUserDetails userDetails) {
        return ResponseEntity.ok(bodyMetricService.getBodyMetricsByUserId(userDetails.getId()));
    }

    @PostMapping
    public ResponseEntity<BodyMetricDto> saveMyMetric(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestBody BodyMetricDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bodyMetricService.saveMetric(userDetails.getId(), dto));
    }
}
