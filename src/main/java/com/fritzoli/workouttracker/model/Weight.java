package com.fritzoli.workouttracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "WEIGHT")
@Data
public class Weight {

    @EmbeddedId
    private UserWeightComposite userWeightComposite;
    @Column(nullable = false)
    private double weight;
}
