package ru.ilyalagutin.workouttracker.dto.workout;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutPlanResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime plannedAt;
    private final List<PlanExerciseResponse> exercises;

    public WorkoutPlanResponse(
            Long id,
            String name,
            String description,
            LocalDateTime plannedAt,
            List<PlanExerciseResponse> exercises) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.plannedAt = plannedAt;
        this.exercises = exercises;
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

    public List<PlanExerciseResponse> getExercises() {
        return exercises;
    }
}
