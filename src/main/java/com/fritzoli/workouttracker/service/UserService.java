package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.BasicLoginRequest;
import com.fritzoli.workouttracker.dto.request.RegisterRequest;
import com.fritzoli.workouttracker.dto.response.UserResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceAlreadyExistsException;
import com.fritzoli.workouttracker.exception.custom.UserNotAuthenticatedException;
import com.fritzoli.workouttracker.model.user.User;
import com.fritzoli.workouttracker.repository.IUserRepository;
import com.fritzoli.workouttracker.service.jwt.JWTService;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final IUserRepository repo;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(IUserRepository repo, AuthenticationManager authManager, JWTService jwtService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    //Todo: mail verification (nur einmal zulassen), db anpassen
    public UserResponse register(RegisterRequest user) {
        if (repo.findByUsername(user.username()).isPresent()) throw new ResourceAlreadyExistsException("There already is a user with the name: " + user.username());
        if (repo.findByEmail(user.email()).isPresent()) throw new ResourceAlreadyExistsException("There already is a user with the email: " + user.email());

        User u = new User(user.username(), user.password(), user.email());
        u.setPassword(encoder.encode(user.password()));
        repo.save(u);
        return new UserResponse(u.getUsername(), u.getEmail(), u.getRole() , u.getCreationdate());
    }

    public String login(BasicLoginRequest user) {

        Authentication authentication =
               authManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));

        if (!authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException("Could not authenticate user with the username: " + user.username());
        }

        return jwtService.generateLoginToken(user.username());
    }

    public void sendVerificationEmail(String username, String email) {
        String token = jwtService.generateEmailVerificationToken(username, email);


    }
}
