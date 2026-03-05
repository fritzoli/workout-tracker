package com.fritzoli.workouttracker.repository;


import com.fritzoli.workouttracker.model.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWeightRepository extends JpaRepository<Weight, String> {

}
