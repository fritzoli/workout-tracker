package com.fritzoli.workouttracker.dto.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse<T> {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final T message;
    private final String path;

    public ErrorResponse(int status, String error, T message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
