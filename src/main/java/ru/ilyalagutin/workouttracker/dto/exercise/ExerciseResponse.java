package ru.ilyalagutin.workouttracker.dto.exercise;

public class ExerciseResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final String category;

    public ExerciseResponse(Long id, String name, String description, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
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

    public String getCategory() {
        return category;
    }
}
