package com.fritzoli.workouttracker.repository.workout;

import com.fritzoli.workouttracker.dto.response.WorkoutResponse;
import com.fritzoli.workouttracker.model.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IWorkoutRepository extends JpaRepository<Workout, String> {
    Optional<Workout> findByNameAndUserId(String name, String userId);
    Optional<Workout> findByIdAndUserId(String workoutId, String userId);
    Iterable<WorkoutResponse> findAllByUserId(String userId);
}