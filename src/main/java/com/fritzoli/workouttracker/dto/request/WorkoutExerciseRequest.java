package com.fritzoli.workouttracker.dto.request;

public record WorkoutExerciseRequest(
        String exerciseId,
        String workoutId,
        int sets,
        int reps,
        int rpe
) {
}
