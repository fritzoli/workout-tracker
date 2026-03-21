package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.request.WorkoutExerciseRequest;
import com.fritzoli.workouttracker.service.WorkoutExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/workout-exercises")
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService) {
        this.workoutExerciseService = workoutExerciseService;
    }

    @PostMapping
    public ResponseEntity<?> createWorkoutExercise(@Valid WorkoutExerciseRequest request) {
        var res = workoutExerciseService.createWorkoutExercise(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


}
