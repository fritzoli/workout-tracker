package com.fritzoli.workouttracker.serive;

import com.fritzoli.workouttracker.dto.UserRequest;
import com.fritzoli.workouttracker.model.User;
import com.fritzoli.workouttracker.repository.IUserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final IUserRepo repo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(IUserRepo repo) {
        this.repo = repo;
    }

    public User register(UserRequest user) {
        User u = new User(user.username(), user.password(), user.email());
        u.setPassword(encoder.encode(user.password()));
        return repo.save(u);
    }
}
