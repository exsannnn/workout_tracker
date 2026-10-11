package ru.ilyalagutin.workouttracker.dto.workout;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class WorkoutSetUpdateRequest {

    @Min(0)
    private int repetitions;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal weight;

    public WorkoutSetUpdateRequest() {
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
