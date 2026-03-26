package com.fritzoli.workouttracker.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BasicLoginRequest(
    @NotBlank(message = "Name should not be empty")
    String username,

    @NotNull(message = "Invalid password")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long")
    String password) {
}
