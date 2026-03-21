package com.fritzoli.workouttracker.repository.workout;

import com.fritzoli.workouttracker.model.workout.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkoutExerciseRepository extends JpaRepository<WorkoutExercise, String> {
}
