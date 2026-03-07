package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.request.BasicLoginRequest;
import com.fritzoli.workouttracker.dto.request.RegisterRequest;
import com.fritzoli.workouttracker.dto.response.UserResponse;
import com.fritzoli.workouttracker.service.MailService;
import com.fritzoli.workouttracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;
    private final MailService mailService;

    public UserController(UserService service, MailService mailService) {
        this.service = service;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest user) {
        var res = service.register(user);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid BasicLoginRequest user) {
        String token = service.login(user);
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.ok().build();
    }

}
