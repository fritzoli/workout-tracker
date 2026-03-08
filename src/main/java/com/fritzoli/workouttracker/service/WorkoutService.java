package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.ExerciseRequest;
import com.fritzoli.workouttracker.dto.response.ExerciseResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceAlreadyExistsException;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.exception.custom.UserNotAuthenticatedException;
import com.fritzoli.workouttracker.model.workout.Exercise;
import com.fritzoli.workouttracker.repository.IExerciseRepository;
import com.fritzoli.workouttracker.repository.IUserRepository;
import jakarta.validation.constraints.Positive;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    private final IExerciseRepository exerciseRepository;
    private final IUserRepository userRepository;

    public WorkoutService(IExerciseRepository exerciseRepository, IUserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    public ExerciseResponse createExercise(ExerciseRequest exercise, UserDetails userDetails) {
        var user = userRepository.findByUsername(userDetails.getUsername());
        var e = exerciseRepository.findExerciseByTitleAndId(exercise.title(), user.get().getId());

        if (e.isPresent())
            throw new ResourceAlreadyExistsException("Exercise already exists");

        Exercise res = new Exercise(user.get(), exercise.title(), exercise.description());
        exerciseRepository.save(res);

        return new ExerciseResponse(res.getId(), res.getTitle(), res.getDescription(), res.getCreationdate());
    }

    public void deleteExercise(String id, UserDetails userDetails) {
        var exercise = exerciseRepository.findById(id);
        if (exercise.isEmpty()) {
            throw new ResourceNotFoundException("Exercise not found");
        }
        if (!exercise.get().getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserNotAuthenticatedException("Wrong Exercise id");
        }
        exerciseRepository.delete(exercise.get());

    }

    public ExerciseResponse updateExercise(String id, ExerciseRequest request, UserDetails userDetails) {
        var e = exerciseRepository.findById(id);
        if (e.isEmpty()) {
            throw new ResourceNotFoundException("Exercise not found");
        }
        var exercise = e.get();

        if (!exercise.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new UserNotAuthenticatedException("Wrong Exercise id");
        }

        if (request.title() != null) exercise.setTitle(request.title());
        if (request.description() != null) exercise.setDescription(request.description());
        exerciseRepository.save(exercise);

        return new ExerciseResponse(exercise.getId(), exercise.getTitle(), exercise.getDescription(), exercise.getCreationdate());
    }

    public Iterable<ExerciseResponse> getExercises(int page,int size, UserDetails userDetails) {
        var user = userRepository.findByUsername(userDetails.getUsername());
        var exercises = exerciseRepository.findExerciseByUserId(user.get().getId(), page, size);

        if (exercises == null)
            throw new ResourceNotFoundException("The user doesn't have any exercises");

        return exercises;
    }
}


