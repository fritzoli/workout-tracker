package com.fritzoli.workouttracker.Controller;

import com.fritzoli.workouttracker.dto.UserRequest;
import com.fritzoli.workouttracker.model.User;
import com.fritzoli.workouttracker.serive.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
