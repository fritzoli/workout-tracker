package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.WorkoutExerciseRequest;
import com.fritzoli.workouttracker.dto.response.WorkoutExerciseResponse;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.model.workout.WorkoutExercise;
import com.fritzoli.workouttracker.repository.workout.IWorkoutExerciseRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkoutExerciseService {
    private final IWorkoutExerciseRepository repository;
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;

    public WorkoutExerciseService(
            IWorkoutExerciseRepository repository,
            ExerciseService exerciseService,
            WorkoutService workoutService
            ) {
        this.repository = repository;
        this.exerciseService = exerciseService;
        this.workoutService = workoutService;
    }

    public WorkoutExerciseResponse createWorkoutExercise(WorkoutExerciseRequest request, IUser user) {
        var exercise = exerciseService.getExerciseById(request.exerciseId(), user);
        var workout = workoutService.getWorkout(request.workoutId(), user);

        var we = new WorkoutExercise(exercise, workout, request.sets(), request.reps(), request.rpe());
        var res = repository.save(we);

        return new WorkoutExerciseResponse(res.getId(),
                exercise.getId(),
                workout.getId(),
                res.getSets(),
                res.getReps(),
                res.getRpe(),
                res.getCreationdate());
    }
}
