package com.fritzoli.workouttracker.repository;


import com.fritzoli.workouttracker.model.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface IWeightRepository extends JpaRepository<Weight, String> {
    Optional<Weight> findByUserIdAndCreationDate(String userId, LocalDate creationdate);
}
