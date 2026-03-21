package com.fritzoli.workouttracker.service.jwt;

import com.fritzoli.workouttracker.dto.request.BasicLoginRequest;
import com.fritzoli.workouttracker.dto.request.RegisterRequest;
import com.fritzoli.workouttracker.dto.response.UserResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceAlreadyExistsException;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.exception.custom.UserNotAuthenticatedException;
import com.fritzoli.workouttracker.model.user.User;
import com.fritzoli.workouttracker.repository.user.IUserRepository;
import com.fritzoli.workouttracker.service.mail.MailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Service
public class AuthorizationService {
    private final IUserRepository repo;
    private final JWTService jwtService;
    private final MailService mailService;
    private final AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AuthorizationService(IUserRepository repo, AuthenticationManager authManager, JWTService jwtService, MailService mailService) {
        this.repo = repo;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.mailService = mailService;
    }

    public void register(RegisterRequest user) {
        if (repo.findByUsername(user.username()).isPresent()) throw new ResourceAlreadyExistsException("There already is a user with the name: " + user.username());
        if (repo.findByEmail(user.email()).isPresent()) throw new ResourceAlreadyExistsException("There already is a user with the email: " + user.email());

        User entity = new User(user.username(), user.password(), user.email());
        entity.setPassword(encoder.encode(user.password()));
        repo.save(entity);

        sendVerificationMail(user.email(), user.username());
    }

    public String login(BasicLoginRequest user) {

        Authentication authentication =
               authManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password()));

        if (!authentication.isAuthenticated())
            throw new UserNotAuthenticatedException("Could not authenticate user with the username: " + user.username());

        return jwtService.generateLoginToken(user.username());
    }

    private void sendVerificationMail(String mail, String username) {
        String token = jwtService.generateMailVerificationToken(username, mail);
        String verifyUrl = "http://localhost:8080/api/v1/auth/verify?token=" +
                URLEncoder.encode(token, StandardCharsets.UTF_8);

        String subject = "Verify your email";
        String message = "Thank you for signing up " + username +
                "\nClick this to verify your email: " + verifyUrl;

        mailService.sendVerificationMail(mail, subject, message);
    }

    public UserResponse verifyToken(String token) {
        jwtService.validateVerifyToken(token);
        String userName = jwtService.extractUserName(token);
        Optional<User> user = repo.findByUsername(userName);

        if (user.isEmpty())
            throw new ResourceNotFoundException("There is no user with the name: " + userName);
        if (user.get().isEnabled())
            throw new ResourceAlreadyExistsException("The user with the name: " + userName + " is already verified");

        User entity = user.get();
        entity.setEnabled(true);
        repo.save(entity);

        return new UserResponse(entity.getUsername(), entity.getEmail(), entity.getRole(), entity.getCreationdate());
    }
}
