package com.fritzoli.workouttracker.model.workout;

import com.fritzoli.workouttracker.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "comment")
    private String comment;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creatediondate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedat;

    public Workout(String name, String comment, User user) {
        this.name = name;
        this.comment = comment;
        this.user = user;
    }
}
