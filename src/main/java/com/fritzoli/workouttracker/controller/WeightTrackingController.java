package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.service.WeightTrackingService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/weight-tracking")
public class WeightTrackingController {
    private final WeightTrackingService weightTrackingService;

    public WeightTrackingController(WeightTrackingService weightTrackingService) {
        this.weightTrackingService = weightTrackingService;
    }

    @PostMapping("/{weight}")
    public ResponseEntity<?> setUserWeight(
            @PathVariable
            @Positive(message = "Weight must be positive") double weight,
            @AuthenticationPrincipal UserDetails userDetails) {
        weightTrackingService.setUserWeight(weight, userDetails);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{weight}")
    public ResponseEntity<?> updateUserWeight(
            @PathVariable
            @Positive(message = "Weight must be positive") double weight,
            @AuthenticationPrincipal UserDetails userDetails) {
        weightTrackingService.updateUserWeight(weight, userDetails);
        return ResponseEntity.ok().build();
    }
}
