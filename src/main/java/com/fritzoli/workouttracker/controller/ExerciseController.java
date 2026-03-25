package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.request.exercise.ExerciseRequest;
import com.fritzoli.workouttracker.dto.response.ExerciseResponse;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.service.ExerciseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(
            @RequestBody @Valid ExerciseRequest exercise,
            @AuthenticationPrincipal IUser userDetails) {

        var res = exerciseService.createExercise(exercise, userDetails);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable String id,
            @AuthenticationPrincipal IUser userDetails) {

        exerciseService.deleteExercise(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponse> updateExercise(
            @PathVariable String id,
            @RequestBody @Valid ExerciseRequest request,
            @AuthenticationPrincipal IUser userDetails) {

        var res = exerciseService.updateExercise(id, request, userDetails);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<ExerciseResponse>> getExercises(
            @Positive(message = "Page number must be positive") int page,
            @Positive(message = "Page size must be positive") int size,
            @AuthenticationPrincipal IUser userDetails) {

        var res = exerciseService.getExercises(page, size, userDetails);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponse> getExerciseById(
            @PathVariable String id,
            @AuthenticationPrincipal IUser userDetails) {
        var res = exerciseService.getExerciseByIdResponse(id, userDetails);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
