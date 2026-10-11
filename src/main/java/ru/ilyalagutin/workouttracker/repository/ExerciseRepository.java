package ru.ilyalagutin.workouttracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilyalagutin.workouttracker.entity.Exercise;
import ru.ilyalagutin.workouttracker.entity.ExerciseCategory;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAllByCategoryOrderByName(ExerciseCategory category);

    List<Exercise> findAllByOrderByName();
}
