package com.fritzoli.workouttracker.repository;

import com.fritzoli.workouttracker.model.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkoutRepository extends JpaRepository<Workout, String> {
}
