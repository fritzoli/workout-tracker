package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.request.workout.CreateWorkoutRequest;
import com.fritzoli.workouttracker.dto.request.workout.UpdateWorkoutRequest;
import com.fritzoli.workouttracker.dto.response.WorkoutResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.model.user.User;
import com.fritzoli.workouttracker.model.workout.Workout;
import com.fritzoli.workouttracker.repository.workout.IWorkoutRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkoutService {
    private final IWorkoutRepository workoutRepository;

    public WorkoutService(IWorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public WorkoutResponse createWorkout(CreateWorkoutRequest request, User userDetails) {
        String comment = request.comment() != null ? request.comment() : "";
        var workout = new Workout(request.name(), comment ,userDetails);

        Optional<Workout> workoutWithSameName = workoutRepository.findByNameAndUserId(request.name(), userDetails.getId());

        if (workoutWithSameName.isPresent())
            throw new IllegalArgumentException("A workout with the same name already exists");

        var res = workoutRepository.save(workout);

        return new WorkoutResponse(res.getId(), res.getName() ,res.getComment() , res.getCreatediondate());
    }

    public void deleteWorkout(String workoutId, IUser user) {
        workoutRepository.findByIdAndUserId(workoutId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find a workout with that id"));

        workoutRepository.deleteById(workoutId);
    }

    public WorkoutResponse updateWorkout(String workoutId, @Valid UpdateWorkoutRequest request, IUser user) {
        Workout workout = workoutRepository.findByIdAndUserId(workoutId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find a workout with that id"));

        Optional<Workout> workoutWithSameName = workoutRepository.findByNameAndUserId(request.getName().get(), user.getId());

        if (workoutWithSameName.isPresent())
            throw new IllegalArgumentException("There is already a workout with that name");

        request.getComment().ifProvided(workout::setComment);
        request.getName().ifProvided(workout::setName);

        Workout res = workoutRepository.save(workout);

        return new WorkoutResponse(res.getId(), res.getName() ,res.getComment() , res.getCreatediondate());
    }

    public WorkoutResponse getWorkout(String workoutId, IUser user) {
        Workout workout = workoutRepository.findByIdAndUserId(workoutId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find a workout with that id"));

        return new WorkoutResponse(workout.getId(), workout.getName(), workout.getComment(), workout.getCreatediondate());
    }

    public Iterable<WorkoutResponse> getAllWorkoutsForUser(IUser userDetails) {
        return workoutRepository.findAllByUserId(userDetails.getId());
    }
}
