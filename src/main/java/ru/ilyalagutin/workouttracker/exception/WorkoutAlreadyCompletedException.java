package ru.ilyalagutin.workouttracker.exception;

public class WorkoutAlreadyCompletedException extends RuntimeException {

    public WorkoutAlreadyCompletedException() {
        super("Workout is already completed");
    }
}
