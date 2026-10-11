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
@Table(name = "workout_sets")
public class WorkoutSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    private int setNumber;

    private int repetitions;

    private BigDecimal weight;

    protected WorkoutSet() {
    }

    public WorkoutSet(Exercise exercise, int setNumber, int repetitions, BigDecimal weight) {
        this.exercise = exercise;
        this.setNumber = setNumber;
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public void update(int repetitions, BigDecimal weight) {
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
