package com.fritzoli.workouttracker.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Weight> weights;

    @Column(name = "creation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;
}