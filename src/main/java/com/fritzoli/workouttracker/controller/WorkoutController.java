package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.request.ExerciseRequest;
import com.fritzoli.workouttracker.dto.response.ExerciseResponse;
import com.fritzoli.workouttracker.model.workout.Exercise;
import com.fritzoli.workouttracker.service.WorkoutService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exercises")
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(
            @RequestBody @Valid ExerciseRequest exercise,
            @AuthenticationPrincipal UserDetails userDetails) {

        var res = workoutService.createExercise(exercise, userDetails);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {

        workoutService.deleteExercise(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponse> updateExercise(
            @PathVariable String id,
            @RequestBody ExerciseRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        var res = workoutService.updateExercise(id, request, userDetails);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<ExerciseResponse>> getExercises(
            @Positive(message = "Page number must be positive") int page,
            @Positive(message = "Page size must be positive") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        var res = workoutService.getExercises(page, size, userDetails);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
