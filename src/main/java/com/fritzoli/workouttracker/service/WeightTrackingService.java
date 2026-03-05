package com.fritzoli.workouttracker.service;

import com.fritzoli.workouttracker.model.Weight;
import com.fritzoli.workouttracker.repository.IUserRepo;
import com.fritzoli.workouttracker.repository.IWeightRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class WeightTrackingService {
    private final IUserRepo userRepo;
    private final IWeightRepository weightRepo;

    public WeightTrackingService(IUserRepo userRepo, IWeightRepository weightRepo) {
        this.userRepo = userRepo;
        this.weightRepo = weightRepo;
    }

    public void setUserWeight(double weight, UserDetails userDetails) {
        var user = userRepo.findByUsername(userDetails.getUsername());
        Weight res = new Weight(user.get(), weight);
        weightRepo.save(res);
    }
}
