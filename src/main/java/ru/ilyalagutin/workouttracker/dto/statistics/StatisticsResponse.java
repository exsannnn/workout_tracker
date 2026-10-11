package ru.ilyalagutin.workouttracker.dto.statistics;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StatisticsResponse {

    private final long completedWorkouts;
    private final long totalSets;
    private final long totalRepetitions;
    private final BigDecimal totalVolume;
    private final BigDecimal maxWeight;
    private final LocalDateTime lastWorkoutAt;

    public StatisticsResponse(
            long completedWorkouts,
            long totalSets,
            long totalRepetitions,
            BigDecimal totalVolume,
            BigDecimal maxWeight,
            LocalDateTime lastWorkoutAt) {
        this.completedWorkouts = completedWorkouts;
        this.totalSets = totalSets;
        this.totalRepetitions = totalRepetitions;
        this.totalVolume = totalVolume;
        this.maxWeight = maxWeight;
        this.lastWorkoutAt = lastWorkoutAt;
    }

    public long getCompletedWorkouts() {
        return completedWorkouts;
    }

    public long getTotalSets() {
        return totalSets;
    }

    public long getTotalRepetitions() {
        return totalRepetitions;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public BigDecimal getMaxWeight() {
        return maxWeight;
    }

    public LocalDateTime getLastWorkoutAt() {
        return lastWorkoutAt;
    }
}
