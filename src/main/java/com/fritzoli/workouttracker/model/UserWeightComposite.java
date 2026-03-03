package com.fritzoli.workouttracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jdk.jfr.Timestamp;

import java.time.LocalDate;

@Embeddable
public class UserWeightComposite {

    @Column(name = "user_id")
    private String userId;
    @Timestamp
    private LocalDate date;

}
