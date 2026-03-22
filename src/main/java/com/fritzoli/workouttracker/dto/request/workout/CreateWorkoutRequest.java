package com.fritzoli.workouttracker.dto.request.workout;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateWorkoutRequest(
        @NotNull(message = "Invalid name")
        @Size(min = 1, max = 30, message = "Name cannot be empty and must be between 1 and 30 characters long")
        String name,

        @Size(min = 1, max = 300, message = "The comment ist too long.")
        String comment
) {
}
