package ru.ilyalagutin.workouttracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ilyalagutin.workouttracker.dto.exercise.ExerciseResponse;
import ru.ilyalagutin.workouttracker.entity.ExerciseCategory;
import ru.ilyalagutin.workouttracker.service.ExerciseService;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<ExerciseResponse> getAll(
            @RequestParam(required = false) ExerciseCategory category) {
        return exerciseService.getAll(category);
    }
}
