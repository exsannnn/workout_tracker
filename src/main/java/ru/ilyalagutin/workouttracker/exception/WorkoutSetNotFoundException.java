package ru.ilyalagutin.workouttracker.exception;

public class WorkoutSetNotFoundException extends RuntimeException {

    public WorkoutSetNotFoundException(Long id) {
        super("Workout set with id " + id + " not found");
    }
}
