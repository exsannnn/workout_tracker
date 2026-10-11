package ru.ilyalagutin.workouttracker.dto.workout;

import jakarta.validation.constraints.Size;

public class CompleteWorkoutRequest {

    @Size(max = 500)
    private String comment;

    public CompleteWorkoutRequest() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
