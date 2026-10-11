package ru.ilyalagutin.workouttracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilyalagutin.workouttracker.dto.exercise.ExerciseResponse;
import ru.ilyalagutin.workouttracker.entity.Exercise;
import ru.ilyalagutin.workouttracker.entity.ExerciseCategory;
import ru.ilyalagutin.workouttracker.repository.ExerciseRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    void getAllReturnsExercises() {
        Exercise exercise = new Exercise(
                "Bench press",
                "Barbell press while lying on a bench",
                ExerciseCategory.STRENGTH);
        when(exerciseRepository.findAllByOrderByName()).thenReturn(List.of(exercise));

        List<ExerciseResponse> responses = exerciseService.getAll(null);

        assertEquals(1, responses.size());
        assertEquals("Bench press", responses.getFirst().getName());
        assertEquals("STRENGTH", responses.getFirst().getCategory());
        verify(exerciseRepository).findAllByOrderByName();
    }

    @Test
    void getAllWithCategoryReturnsOnlyCategoryExercises() {
        Exercise exercise = new Exercise(
                "Running",
                "Running at a comfortable pace",
                ExerciseCategory.CARDIO);
        when(exerciseRepository.findAllByCategoryOrderByName(ExerciseCategory.CARDIO))
                .thenReturn(List.of(exercise));

        List<ExerciseResponse> responses = exerciseService.getAll(ExerciseCategory.CARDIO);

        assertEquals(1, responses.size());
        assertEquals("Running", responses.getFirst().getName());
        verify(exerciseRepository).findAllByCategoryOrderByName(ExerciseCategory.CARDIO);
    }
}
