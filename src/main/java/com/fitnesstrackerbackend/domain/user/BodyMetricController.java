package com.fitnesstrackerbackend.domain.user;

import com.fitnesstrackerbackend.domain.user.dto.BodyMetricDto;
import com.fitnesstrackerbackend.core.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/me/metrics")
@RequiredArgsConstructor
public class BodyMetricController {

    private final BodyMetricService bodyMetricService;

    @GetMapping
    public ResponseEntity<List<BodyMetricDto>> getMyMetrics() {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(bodyMetricService.getBodyMetricsByUserId(userId));
    }
}
