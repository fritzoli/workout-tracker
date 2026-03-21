package com.fritzoli.workouttracker.repository.workout;

import com.fritzoli.workouttracker.dto.response.ExerciseResponse;
import com.fritzoli.workouttracker.model.workout.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IExerciseRepository extends JpaRepository<Exercise, String> {
    Optional<Exercise> findExerciseByTitleAndId(String name, String id);
    Iterable<ExerciseResponse> findExerciseByUserId(String title, int page, int size);
}
