package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.model.User;
import com.fritzoli.workouttracker.model.UserPrincipal;
import com.fritzoli.workouttracker.repository.IUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final IUserRepo repo;

    public MyUserDetailsService(IUserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repo.findByUsername(username);

        if (user.isEmpty()) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user.get());
    }
}
