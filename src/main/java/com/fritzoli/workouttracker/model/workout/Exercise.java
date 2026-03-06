package com.fritzoli.workouttracker.model.workout;

import com.fritzoli.workouttracker.model.user.User;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "EXERCISE")
@Getter
@Setter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    @Timestamp
    @Column(name = "creation_date")
    private LocalDateTime creationdate;
}
