package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.service.WeightTrackingService;
import jakarta.validation.constraints.Positive;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weight-tracking")
public class WeightTrackingController {
    private final WeightTrackingService weightTrackingService;

    public WeightTrackingController(WeightTrackingService weightTrackingService) {
        this.weightTrackingService = weightTrackingService;
    }

    @PostMapping("/{weight}")
    public void setUserWeight(@PathVariable @Positive double weight, @AuthenticationPrincipal UserDetails userDetails) {
        weightTrackingService.setUserWeight(weight, userDetails);
    }
}
