package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.model.user.User;
import com.fritzoli.workouttracker.model.user.UserPrincipal;
import com.fritzoli.workouttracker.repository.IUserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NullMarked
public class MyUserDetailsService implements UserDetailsService {
    private final IUserRepository repo;

    public MyUserDetailsService(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repo.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user.get());
    }
}
