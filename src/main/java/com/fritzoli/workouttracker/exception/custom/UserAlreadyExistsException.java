package com.fritzoli.workouttracker.exception.custom;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super("There is already a User with that " + message);
    }
}
