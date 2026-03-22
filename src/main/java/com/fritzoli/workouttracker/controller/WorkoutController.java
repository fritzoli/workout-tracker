package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.request.workout.CreateWorkoutRequest;
import com.fritzoli.workouttracker.dto.request.workout.UpdateWorkoutRequest;
import com.fritzoli.workouttracker.dto.response.WorkoutResponse;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.model.user.User;
import com.fritzoli.workouttracker.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping
    public ResponseEntity<WorkoutResponse> createWorkout(
            @RequestBody @Valid CreateWorkoutRequest request,
            @AuthenticationPrincipal User userDetails) {

        var response = workoutService.createWorkout(request, userDetails);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<?> deleteWorkout(
            @PathVariable String workoutId,
            @AuthenticationPrincipal IUser userDetails) {

        workoutService.deleteWorkout(workoutId, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponse> updateWorkout(
            @PathVariable String workoutId,
            @RequestBody @Valid UpdateWorkoutRequest request,
            @AuthenticationPrincipal IUser userDetails) {

        var response = workoutService.updateWorkout(workoutId, request, userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
