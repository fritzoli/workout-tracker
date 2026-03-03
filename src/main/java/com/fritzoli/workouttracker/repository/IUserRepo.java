package com.fritzoli.workouttracker.repository;

import com.fritzoli.workouttracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User, String> {
    User findByUsername(String username);
}
