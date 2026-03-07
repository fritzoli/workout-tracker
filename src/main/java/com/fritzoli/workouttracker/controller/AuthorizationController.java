package com.fritzoli.workouttracker.controller;

import com.fritzoli.workouttracker.dto.request.BasicLoginRequest;
import com.fritzoli.workouttracker.dto.request.RegisterRequest;
import com.fritzoli.workouttracker.dto.response.UserResponse;
import com.fritzoli.workouttracker.service.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {
    private final AuthorizationService service;

    public AuthorizationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest user) {
        service.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
