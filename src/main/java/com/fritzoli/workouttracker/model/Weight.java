package com.fritzoli.workouttracker.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "WEIGHT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Weight {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDate creationDate;

    @Column(nullable = false)
    private double weight;

    public Weight(User user, double weight) {
        this.user = user;
        this.weight = weight;
    }
}
