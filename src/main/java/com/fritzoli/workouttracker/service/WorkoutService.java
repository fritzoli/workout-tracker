package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.workout.CreateWorkoutRequest;
import com.fritzoli.workouttracker.dto.request.workout.UpdateWorkoutRequest;
import com.fritzoli.workouttracker.dto.response.WorkoutResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.exception.custom.UserNotAuthenticatedException;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.model.user.User;
import com.fritzoli.workouttracker.model.workout.Workout;
import com.fritzoli.workouttracker.repository.workout.IWorkoutRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {
    private final IWorkoutRepository workoutRepository;

    public WorkoutService(IWorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public WorkoutResponse createWorkout(CreateWorkoutRequest request, User userDetails) {
        String comment = request.comment() != null ? request.comment() : "";
        var workout = new Workout(request.name(), comment ,userDetails);
        var res = workoutRepository.save(workout);

        return new WorkoutResponse(res.getId(), res.getName() ,res.getComment() , res.getCreatediondate());
    }

    public void deleteWorkout(String workoutId, IUser user) {
        var workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find a workout with that id"));

        if (!workout.getUser().getId().equals(user.getId()))
            throw new UserNotAuthenticatedException("Unable to find a workout with that id");

        workoutRepository.deleteById(workoutId);
    }

    public WorkoutResponse updateWorkout(String workoutId, @Valid UpdateWorkoutRequest request, IUser user) {
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find a workout with that id"));

        if (!workout.getUser().getId().equals(user.getId()))
            throw new UserNotAuthenticatedException("Unable to find a workout with that id");

        request.getComment().ifProvided(workout::setComment);
        request.getName().ifProvided(workout::setName);
        Workout res = workoutRepository.save(workout);

        return new WorkoutResponse(res.getId(), res.getName() ,res.getComment() , res.getCreatediondate());
    }
}
