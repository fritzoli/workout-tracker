package com.fritzoli.workouttracker.dto.request;

import jakarta.validation.constraints.Size;

public record ExerciseRequest(
        @Size(max = 20, message = "Title may be a maximum of 20 characters long")
        String title,
        @Size(max = 500, message = "Title may be a maximum of 20 characters long")
        String description
) {
}
