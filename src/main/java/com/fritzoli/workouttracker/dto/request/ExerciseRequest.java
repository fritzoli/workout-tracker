package com.fritzoli.workouttracker.dto.request;

import jakarta.validation.constraints.Max;

public record ExerciseRequest(
        @Max(value = 20, message = "Title may be a maximum of 20 characters long")
        String title,
        @Max(value = 500, message = "Title may be a maximum of 20 characters long")
        String description
) {
}
