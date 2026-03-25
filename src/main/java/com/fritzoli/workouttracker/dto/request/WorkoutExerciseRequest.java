package com.fritzoli.workouttracker.dto.request;

import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Range;

public record WorkoutExerciseRequest(
        String exerciseId,
        String workoutId,
        @Positive(message = "Sets must be a positive number")
        int sets,
        @Positive(message = "Reps must be a positive number")
        int reps,
        @Range(min = 1, max = 10, message = "RPE must be between 1 and 10")
        int rpe
) {
}
