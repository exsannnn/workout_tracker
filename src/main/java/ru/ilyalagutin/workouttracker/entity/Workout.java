package ru.ilyalagutin.workouttracker.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String comment;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    private WorkoutStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutSet> sets = new ArrayList<>();

    protected Workout() {
    }

    public Workout(WorkoutPlan workoutPlan, User user) {
        this.name = workoutPlan.getName();
        this.startedAt = LocalDateTime.now();
        this.status = WorkoutStatus.IN_PROGRESS;
        this.user = user;
        this.workoutPlan = workoutPlan;
    }

    public void addSet(WorkoutSet workoutSet) {
        sets.add(workoutSet);
        workoutSet.setWorkout(this);
    }

    public void complete(String comment) {
        this.comment = comment;
        this.completedAt = LocalDateTime.now();
        this.status = WorkoutStatus.COMPLETED;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public WorkoutStatus getStatus() {
        return status;
    }

    public List<WorkoutSet> getSets() {
        return sets;
    }

    public WorkoutPlan getWorkoutPlan() {
        return workoutPlan;
    }
}
