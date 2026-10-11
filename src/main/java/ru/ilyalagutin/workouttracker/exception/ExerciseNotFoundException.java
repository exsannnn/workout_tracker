package ru.ilyalagutin.workouttracker.exception;

public class ExerciseNotFoundException extends RuntimeException {

    public ExerciseNotFoundException(Long id) {
        super("Exercise with id " + id + " not found");
    }
}
