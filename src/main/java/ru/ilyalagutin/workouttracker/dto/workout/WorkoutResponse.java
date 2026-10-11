package ru.ilyalagutin.workouttracker.dto.workout;

import java.time.LocalDateTime;
import java.util.List;

public class WorkoutResponse {

    private final Long id;
    private final Long workoutPlanId;
    private final String name;
    private final String comment;
    private final String status;
    private final LocalDateTime startedAt;
    private final LocalDateTime completedAt;
    private final List<WorkoutSetResponse> sets;

    public WorkoutResponse(
            Long id,
            Long workoutPlanId,
            String name,
            String comment,
            String status,
            LocalDateTime startedAt,
            LocalDateTime completedAt,
            List<WorkoutSetResponse> sets) {
        this.id = id;
        this.workoutPlanId = workoutPlanId;
        this.name = name;
        this.comment = comment;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.sets = sets;
    }

    public Long getId() {
        return id;
    }

    public Long getWorkoutPlanId() {
        return workoutPlanId;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public List<WorkoutSetResponse> getSets() {
        return sets;
    }
}
