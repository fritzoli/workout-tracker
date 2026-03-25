package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.request.WorkoutExerciseRequest;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.service.WorkoutExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workout-exercises")
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @PostMapping
    public ResponseEntity<?> createWorkoutExercise(
            @Valid @RequestBody WorkoutExerciseRequest request,
            @AuthenticationPrincipal IUser userDetails) {

        var res = workoutExerciseService.createWorkoutExercise(request, userDetails);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutExercise(
            @PathVariable String id,
            @AuthenticationPrincipal IUser userDetails) {

        workoutExerciseService.deleteWorkoutExercise(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{workoutExerciseId}")
    public ResponseEntity<?> getWorkoutExercise(
            @PathVariable String workoutExerciseId,
            @AuthenticationPrincipal IUser userDetails) {

        var response = workoutExerciseService.getWorkoutExerciseById(workoutExerciseId, userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
