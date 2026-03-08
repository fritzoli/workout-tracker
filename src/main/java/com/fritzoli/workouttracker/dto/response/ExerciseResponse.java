package com.fritzoli.workouttracker.dto.response;

import java.time.LocalDateTime;

public record ExerciseResponse(
        String id,
        String title,
        String description,
        LocalDateTime creationDate
) {
}
