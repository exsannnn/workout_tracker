package ru.ilyalagutin.workouttracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "plan_exercises")
public class PlanExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id")
    private WorkoutPlan workoutPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private int setCount;

    private int repetitions;

    private BigDecimal weight;

    protected PlanExercise() {
    }

    public PlanExercise(Exercise exercise, int setCount, int repetitions, BigDecimal weight) {
        this.exercise = exercise;
        this.setCount = setCount;
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public void setWorkoutPlan(WorkoutPlan workoutPlan) {
        this.workoutPlan = workoutPlan;
    }

    public Long getId() {
        return id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getSetCount() {
        return setCount;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
