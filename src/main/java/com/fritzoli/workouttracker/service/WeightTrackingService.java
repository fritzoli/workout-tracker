package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.dto.response.WeightResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceAlreadyExistsException;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.model.user.Weight;
import com.fritzoli.workouttracker.repository.IUserRepository;
import com.fritzoli.workouttracker.repository.IWeightRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class WeightTrackingService {
    private final IUserRepository userRepo;
    private final IWeightRepository weightRepo;

    public WeightTrackingService(IUserRepository userRepo, IWeightRepository weightRepo) {
        this.userRepo = userRepo;
        this.weightRepo = weightRepo;
    }

    public WeightResponse setUserWeight(double weight, UserDetails userDetails) {
        var user = userRepo.findByUsername(userDetails.getUsername());
        var dailyWeight = weightRepo.findByUserIdAndCreationDate(user.get().getId(), LocalDate.now());

        if (dailyWeight.isPresent()) {
            throw new ResourceAlreadyExistsException("There is already a weight entry for today");
        }

        Weight res = new Weight(user.get(), weight);
        weightRepo.save(res);

        return new WeightResponse(res.getId(), res.getWeight(), res.getCreationDate());
    }

    public WeightResponse updateUserWeight(double weight, UserDetails userDetails) {
        var user = userRepo.findByUsername(userDetails.getUsername());
        var dailyWeight = weightRepo.findByUserIdAndCreationDate(user.get().getId(), LocalDate.now());

        if (dailyWeight.isEmpty()) {
            throw new ResourceNotFoundException("There is no weight entry for today");
        }

        Weight res = dailyWeight.get();
        res.setWeight(weight);
        weightRepo.save(res);

        return new WeightResponse(res.getId(), res.getWeight(), res.getCreationDate());
    }

}
