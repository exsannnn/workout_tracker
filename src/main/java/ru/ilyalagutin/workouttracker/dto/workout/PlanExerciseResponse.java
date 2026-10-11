package ru.ilyalagutin.workouttracker.dto.workout;

import java.math.BigDecimal;

public class PlanExerciseResponse {

    private final Long exerciseId;
    private final String exerciseName;
    private final int setCount;
    private final int repetitions;
    private final BigDecimal weight;

    public PlanExerciseResponse(
            Long exerciseId,
            String exerciseName,
            int setCount,
            int repetitions,
            BigDecimal weight) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.setCount = setCount;
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getSetCount() {
        return setCount;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
