package com.fritzoli.workouttracker.dto.response;

import java.time.LocalDateTime;

public record WorkoutResponse(
        String id,
        String name,
        String comment,
        LocalDateTime creatediondate
) {
}

