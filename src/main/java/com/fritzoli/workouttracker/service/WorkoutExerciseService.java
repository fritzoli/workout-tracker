package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.WorkoutExerciseRequest;
import com.fritzoli.workouttracker.dto.response.WorkoutExerciseResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.exception.custom.UserNotAuthenticatedException;
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

    public void deleteWorkoutExercise(String id, IUser userDetails) {
        var we = repository.findById(id);

        if (we.isEmpty())
            throw new ResourceNotFoundException("Workout exercise not found");

        // Prevents other users from deleting exercises
        if (!we.get().getWorkout().getUser().getId().equals(userDetails.getId()))
            throw new UserNotAuthenticatedException("Wrong workout exercise id");

        repository.delete(we.get());
    }

    public WorkoutExerciseResponse getWorkoutExerciseById(String workoutExerciseId, IUser userDetails) {
        var we = repository.findById(workoutExerciseId);

        if (we.isEmpty() || !we.get().getWorkout().getUser().getId().equals(userDetails.getId()))
            throw new ResourceNotFoundException("Exercise not found");
        var res = we.get();

        return new WorkoutExerciseResponse(res.getId(),
                res.getExercise().getId(),
                res.getWorkout().getId(),
                res.getSets(),
                res.getReps(),
                res.getRpe(),
                res.getCreationdate());
    }
}
