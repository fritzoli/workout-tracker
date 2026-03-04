package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.UserRequest;
import com.fritzoli.workouttracker.exception.custom.UserAlreadyExistsException;
import com.fritzoli.workouttracker.model.User;
import com.fritzoli.workouttracker.repository.IUserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    private final IUserRepo repo;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(IUserRepo repo, AuthenticationManager authManager, JWTService jwtService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public void register(UserRequest user) {
        if (repo.findByUsername(user.username()).isPresent()) throw new UserAlreadyExistsException("username");
        if (repo.findByEmail(user.email()).isPresent()) throw new UserAlreadyExistsException("email");

        User u = new User(user.username(), user.password(), user.email());
        u.setPassword(encoder.encode(user.password()));
        repo.save(u);
    }

    public String login(UserRequest user) throws NoSuchAlgorithmException {
       Authentication authentication =
               authManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));

       if (authentication.isAuthenticated()){
           return jwtService.generateToken(user.username());
       }

       return null;
    }
}
