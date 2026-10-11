package ru.ilyalagutin.workouttracker.dto.workout;

import java.math.BigDecimal;

public class WorkoutSetResponse {

    private final Long id;
    private final Long exerciseId;
    private final String exerciseName;
    private final int setNumber;
    private final int repetitions;
    private final BigDecimal weight;

    public WorkoutSetResponse(
            Long id,
            Long exerciseId,
            String exerciseName,
            int setNumber,
            int repetitions,
            BigDecimal weight) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.setNumber = setNumber;
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public BigDecimal getWeight() {
        return weight;
    }
}
