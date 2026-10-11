package ru.ilyalagutin.workouttracker.exception;

public class WorkoutPlanNotFoundException extends RuntimeException {

    public WorkoutPlanNotFoundException(Long id) {
        super("Workout plan with id " + id + " not found");
    }
}
