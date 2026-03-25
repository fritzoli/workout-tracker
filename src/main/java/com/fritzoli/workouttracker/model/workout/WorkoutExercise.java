package com.fritzoli.workouttracker.model.workout;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "WORKOUT_EXERCISE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "workout_id", referencedColumnName = "id")
    private Workout workout;

    @Column(nullable = false)
    private int sets;

    @Column(nullable = false)
    private int reps;

    @Column(nullable = false)
    private int rpe;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationdate;

    public WorkoutExercise(Exercise exercise, Workout workout, int sets, int reps, int rpe) {
        this.exercise = exercise;
        this.workout = workout;
        this.sets = sets;
        this.reps = reps;
        this.rpe = rpe;
    }
}
