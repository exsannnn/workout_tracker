package ru.ilyalagutin.workouttracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilyalagutin.workouttracker.dto.workout.PlanExerciseRequest;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutPlanRequest;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutPlanResponse;
import ru.ilyalagutin.workouttracker.entity.Exercise;
import ru.ilyalagutin.workouttracker.entity.ExerciseCategory;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.entity.UserRole;
import ru.ilyalagutin.workouttracker.exception.WorkoutPlanNotFoundException;
import ru.ilyalagutin.workouttracker.repository.ExerciseRepository;
import ru.ilyalagutin.workouttracker.repository.WorkoutPlanRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkoutPlanServiceTest {

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private WorkoutPlanService workoutPlanService;

    @Test
    void createAddsExercisesToWorkoutPlan() {
        User user = new User("user@example.com", "password", UserRole.USER);
        Exercise exercise = new Exercise(
                "Bench press",
                "Barbell press while lying on a bench",
                ExerciseCategory.STRENGTH);
        WorkoutPlanRequest request = createRequest();

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(workoutPlanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        WorkoutPlanResponse response = workoutPlanService.create(request, user);

        assertEquals("Chest workout", response.getName());
        assertEquals(1, response.getExercises().size());
        assertEquals("Bench press", response.getExercises().getFirst().getExerciseName());
        verify(workoutPlanRepository).save(any());
    }

    @Test
    void getByIdThrowsExceptionWhenUserDoesNotHavePlan() {
        User user = new User("user@example.com", "password", UserRole.USER);
        when(workoutPlanRepository.findByIdAndUserId(anyLong(), any()))
                .thenReturn(Optional.empty());

        assertThrows(
                WorkoutPlanNotFoundException.class,
                () -> workoutPlanService.getById(1L, user));
    }

    private WorkoutPlanRequest createRequest() {
        PlanExerciseRequest exerciseRequest = new PlanExerciseRequest();
        exerciseRequest.setExerciseId(1L);
        exerciseRequest.setSetCount(3);
        exerciseRequest.setRepetitions(10);
        exerciseRequest.setWeight(BigDecimal.valueOf(60));

        WorkoutPlanRequest request = new WorkoutPlanRequest();
        request.setName("Chest workout");
        request.setDescription("Training for chest muscles");
        request.setPlannedAt(LocalDateTime.of(2026, 10, 12, 18, 0));
        request.setExercises(List.of(exerciseRequest));

        return request;
    }
}
