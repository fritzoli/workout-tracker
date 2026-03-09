package com.fritzoli.workouttracker.model.user;

import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

public interface IUser extends UserDetails {
    String getEmail();
    LocalDateTime getCreationdate();
    String getId();
}
