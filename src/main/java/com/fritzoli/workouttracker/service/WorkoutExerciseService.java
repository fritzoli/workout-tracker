package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.WorkoutExerciseRequest;
import com.fritzoli.workouttracker.dto.response.WorkoutExerciseResponse;
import com.fritzoli.workouttracker.repository.workout.IWorkoutExerciseRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WorkoutExerciseService {
    private final IWorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExerciseService(IWorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public WorkoutExerciseResponse createWorkoutExercise(WorkoutExerciseRequest request) {
        // Todo: workout id prüfen
        // checken ob objekt schon existiert
        var exercise = workoutExerciseRepository.findById(request.exerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));

        //var WorkoutExercise = new WorkoutExercise(request.workoutId(), request.sets(), request.reps());
        return null;
    }
}
