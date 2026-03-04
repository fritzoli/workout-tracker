package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.UserRequest;
import com.fritzoli.workouttracker.model.User;
import com.fritzoli.workouttracker.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody UserRequest user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequest user) throws NoSuchAlgorithmException {
        return service.login(user);
    }

}
