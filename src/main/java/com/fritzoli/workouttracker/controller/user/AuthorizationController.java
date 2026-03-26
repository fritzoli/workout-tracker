package com.fritzoli.workouttracker.controller.user;

import com.fritzoli.workouttracker.dto.request.user.BasicLoginRequest;
import com.fritzoli.workouttracker.dto.request.user.RegisterRequest;
import com.fritzoli.workouttracker.dto.response.UserResponse;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.service.jwt.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/verify")
    public ResponseEntity<UserResponse> verifyEmail(@RequestParam String token) {
        var res = service.verifyToken(token);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid BasicLoginRequest user) {
        String token = service.login(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<?> isAuthenticated(@AuthenticationPrincipal IUser user) {
        var res = service.isAuthenticated(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
