package ru.ilyalagutin.workouttracker.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_plans")
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private LocalDateTime plannedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanExercise> exercises = new ArrayList<>();

    protected WorkoutPlan() {
    }

    public WorkoutPlan(String name, String description, LocalDateTime plannedAt, User user) {
        this.name = name;
        this.description = description;
        this.plannedAt = plannedAt;
        this.user = user;
    }

    public void update(String name, String description, LocalDateTime plannedAt) {
        this.name = name;
        this.description = description;
        this.plannedAt = plannedAt;
    }

    public void addExercise(PlanExercise exercise) {
        exercises.add(exercise);
        exercise.setWorkoutPlan(this);
    }

    public void clearExercises() {
        exercises.clear();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getPlannedAt() {
        return plannedAt;
    }

    public User getUser() {
        return user;
    }

    public List<PlanExercise> getExercises() {
        return exercises;
    }
}
