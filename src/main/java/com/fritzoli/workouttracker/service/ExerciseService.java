package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.ExerciseRequest;
import com.fritzoli.workouttracker.dto.response.ExerciseResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceAlreadyExistsException;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.exception.custom.UserNotAuthenticatedException;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.model.workout.Exercise;
import com.fritzoli.workouttracker.repository.workout.IExerciseRepository;
import com.fritzoli.workouttracker.repository.user.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {
    private final IExerciseRepository exerciseRepository;
    private final IUserRepository userRepository;

    public ExerciseService(IExerciseRepository exerciseRepository, IUserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    public ExerciseResponse createExercise(ExerciseRequest exercise, IUser userDetails) {
        var e = exerciseRepository.findExerciseByTitleAndId(exercise.title(), userDetails.getId());

        if (e.isPresent())
            throw new ResourceAlreadyExistsException("Exercise already exists");

        var user = userRepository.getReferenceById(userDetails.getId());
        Exercise res = new Exercise(user, exercise.title(), exercise.description());
        exerciseRepository.save(res);

        return new ExerciseResponse(res.getId(), res.getTitle(), res.getDescription(), res.getCreationdate());
    }

    public void deleteExercise(String id, IUser userDetails) {
        var exercise = exerciseRepository.findById(id);

        if (exercise.isEmpty())
            throw new ResourceNotFoundException("Exercise not found");

        // Prevents other users from deleting exercises
        if (!exercise.get().getUser().getId().equals(userDetails.getId()))
            throw new UserNotAuthenticatedException("Wrong Exercise id");

        exerciseRepository.delete(exercise.get());

    }

    public ExerciseResponse updateExercise(String id, ExerciseRequest request, IUser userDetails) {
        var e = exerciseRepository.findById(id);

        if (e.isEmpty())
            throw new ResourceNotFoundException("Exercise not found");

        var exercise = e.get();

        // Prevents other users from deleting exercises
        if (!exercise.getUser().getId().equals(userDetails.getId())) {
            throw new UserNotAuthenticatedException("Wrong Exercise id");
        }

        if (request.title() != null) exercise.setTitle(request.title());
        if (request.description() != null) exercise.setDescription(request.description());
        exerciseRepository.save(exercise);

        return new ExerciseResponse(exercise.getId(), exercise.getTitle(), exercise.getDescription(), exercise.getCreationdate());
    }

    public Iterable<ExerciseResponse> getExercises(int page,int size, IUser userDetails) {
        var exercises = exerciseRepository.findExerciseByUserId(userDetails.getId(), page, size);

        if (exercises == null)
            throw new ResourceNotFoundException("The user doesn't have any exercises");

        return exercises;
    }

    public ExerciseResponse getExerciseById(String id, IUser userDetails) {
        var exercise = exerciseRepository.findById(id);

        if (exercise.isEmpty() || !exercise.get().getUser().getId().equals(userDetails.getId()))
            throw new ResourceNotFoundException("Exercise not found");

        var e = exercise.get();
        return new ExerciseResponse(e.getId(), e.getTitle(), e.getDescription(), e.getCreationdate());
    }
}


