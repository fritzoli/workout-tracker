package com.fritzoli.workouttracker.dto.response;

import java.time.LocalDateTime;

public record WorkoutExerciseResponse(
        String id,
        String exerciseId,
        String workoutId,
        int sets,
        int reps,
        int rpe,
        LocalDateTime creationdate
) {
}
