package com.fritzoli.workouttracker.controller.user;

import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.service.user.WeightTrackingService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/weights")
public class WeightTrackingController {
    private final WeightTrackingService weightTrackingService;

    public WeightTrackingController(WeightTrackingService weightTrackingService) {
        this.weightTrackingService = weightTrackingService;
    }

    @PostMapping("/{weight}")
    public ResponseEntity<?> setUserWeight(
            @PathVariable
            @Positive(message = "Weight must be positive") double weight,
            @AuthenticationPrincipal IUser userDetails) {
        var res = weightTrackingService.setUserWeight(weight, userDetails);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PatchMapping("/{weight}")
    public ResponseEntity<?> updateUserWeight(
            @PathVariable
            @Positive(message = "Weight must be positive") double weight,
            @AuthenticationPrincipal IUser userDetails) {
        var res = weightTrackingService.updateUserWeight(weight, userDetails);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
