package com.fritzoli.workouttracker.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Name should not be empty")
        String username,

        @NotNull(message = "Invalid password")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long")
        String password,

        @NotNull(message = "Email should not be empty")
        @Email(regexp = ".*(\\.com|\\.de|\\.org|\\.net|\\.cc)$", message = "Email must be in a proper email format")
        String email)  {
}
