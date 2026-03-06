package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.request.ExerciseRequest;
import com.fritzoli.workouttracker.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<?> createExercise(
            @RequestBody @Valid ExerciseRequest exercise,
            @AuthenticationPrincipal UserDetails userDetails) {
        workoutService.createExercise(exercise, userDetails);
        return ResponseEntity.ok().build();
    }
}
