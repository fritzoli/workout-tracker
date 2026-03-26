package com.fritzoli.workouttracker.model.workout;

import com.fritzoli.workouttracker.model.user.User;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "EXERCISE")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationdate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedat;

    public Exercise(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;
    }
}
