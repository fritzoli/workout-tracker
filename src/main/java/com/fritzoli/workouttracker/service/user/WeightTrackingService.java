package com.fritzoli.workouttracker.service.user;

import com.fritzoli.workouttracker.dto.response.WeightResponse;
import com.fritzoli.workouttracker.exception.custom.ResourceAlreadyExistsException;
import com.fritzoli.workouttracker.exception.custom.ResourceNotFoundException;
import com.fritzoli.workouttracker.model.user.IUser;
import com.fritzoli.workouttracker.model.user.User;
import com.fritzoli.workouttracker.model.user.Weight;
import com.fritzoli.workouttracker.repository.user.IUserRepository;
import com.fritzoli.workouttracker.repository.user.IWeightRepository;
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

    public WeightResponse setUserWeight(double weight, IUser userDetails) {
        var dailyWeight = weightRepo.findByUserIdAndCreationDate(userDetails.getId(), LocalDate.now());

        if (dailyWeight.isPresent())
            throw new ResourceAlreadyExistsException("There is already a weight entry for today");

        User user = userRepo.getReferenceById(userDetails.getId());
        Weight res = new Weight(user, weight);
        weightRepo.save(res);

        return new WeightResponse(res.getId(), res.getWeight(), res.getCreationDate());
    }

    public WeightResponse updateUserWeight(double weight, IUser userDetails) {
        var dailyWeight = weightRepo.findByUserIdAndCreationDate(userDetails.getId(), LocalDate.now());

        if (dailyWeight.isEmpty())
            throw new ResourceNotFoundException("There is no weight entry for today");

        Weight res = dailyWeight.get();
        res.setWeight(weight);
        weightRepo.save(res);

        return new WeightResponse(res.getId(), res.getWeight(), res.getCreationDate());
    }

}
