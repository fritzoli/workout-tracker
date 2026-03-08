package com.fritzoli.workouttracker.dto.response;

import com.fritzoli.workouttracker.model.user.UserRole;

import java.time.LocalDateTime;

public record UserResponse(
    String username,
    String email,
    UserRole role,
    LocalDateTime creationdate
) {
}
