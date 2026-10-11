package ru.ilyalagutin.workouttracker.dto.workout;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutPlanRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    private LocalDateTime plannedAt;

    @NotEmpty
    private List<@Valid PlanExerciseRequest> exercises;

    public WorkoutPlanRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPlannedAt() {
        return plannedAt;
    }

    public void setPlannedAt(LocalDateTime plannedAt) {
        this.plannedAt = plannedAt;
    }

    public List<PlanExerciseRequest> getExercises() {
        return exercises;
    }

    public void setExercises(List<PlanExerciseRequest> exercises) {
        this.exercises = exercises;
    }
}
