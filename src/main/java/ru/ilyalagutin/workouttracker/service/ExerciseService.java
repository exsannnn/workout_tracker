package ru.ilyalagutin.workouttracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilyalagutin.workouttracker.dto.exercise.ExerciseResponse;
import ru.ilyalagutin.workouttracker.entity.Exercise;
import ru.ilyalagutin.workouttracker.entity.ExerciseCategory;
import ru.ilyalagutin.workouttracker.repository.ExerciseRepository;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponse> getAll(ExerciseCategory category) {
        List<Exercise> exercises;

        if (category == null) {
            exercises = exerciseRepository.findAllByOrderByName();
        } else {
            exercises = exerciseRepository.findAllByCategoryOrderByName(category);
        }

        return exercises.stream()
                .map(this::toResponse)
                .toList();
    }

    private ExerciseResponse toResponse(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getCategory().name());
    }
}
