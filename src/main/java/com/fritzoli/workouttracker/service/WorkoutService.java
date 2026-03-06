package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.ExerciseRequest;
import com.fritzoli.workouttracker.exception.custom.ResourceAlreadyExistsException;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.model.workout.Exercise;
import com.fritzoli.workouttracker.repository.IExerciseRepository;
import com.fritzoli.workouttracker.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {
    private final IExerciseRepository exerciseRepository;
    private final IUserRepository userRepository;

    public WorkoutService(IExerciseRepository exerciseRepository, IUserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    public void createExercise(ExerciseRequest exercise, UserDetails userDetails) {
        var e = exerciseRepository.findExerciseByTitle(userDetails.getUsername());
        if (e.isPresent()) {
            throw new ResourceAlreadyExistsException("Exercise already exists");
        }
        var user = userRepository.findByUsername(userDetails.getUsername());
        Exercise res = new Exercise(user.get(), exercise.title(), exercise.description());
        exerciseRepository.save(res);
    }
}


