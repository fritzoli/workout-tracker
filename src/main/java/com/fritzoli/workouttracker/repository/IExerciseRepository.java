package com.fritzoli.workouttracker.repository;

import com.fritzoli.workouttracker.model.workout.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IExerciseRepository extends JpaRepository<Exercise, String> {
    Optional<Exercise> findExerciseByTitle(String name);
}
