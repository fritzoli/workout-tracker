package com.fritzoli.workouttracker.dto.response;


import java.time.LocalDate;

public record WeightResponse(
        String id,
        double weight,
        LocalDate creationdate
) {
}
