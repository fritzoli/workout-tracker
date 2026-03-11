package com.fritzoli.workouttracker.model.workout;

import com.fritzoli.workouttracker.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "WORKOUT")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "comment")
    private String comment;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creatediondate;
}
