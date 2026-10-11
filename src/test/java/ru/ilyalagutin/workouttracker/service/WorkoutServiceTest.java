package ru.ilyalagutin.workouttracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilyalagutin.workouttracker.dto.workout.CompleteWorkoutRequest;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutResponse;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutSetUpdateRequest;
import ru.ilyalagutin.workouttracker.entity.Exercise;
import ru.ilyalagutin.workouttracker.entity.ExerciseCategory;
import ru.ilyalagutin.workouttracker.entity.PlanExercise;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.entity.UserRole;
import ru.ilyalagutin.workouttracker.entity.Workout;
import ru.ilyalagutin.workouttracker.entity.WorkoutPlan;
import ru.ilyalagutin.workouttracker.entity.WorkoutSet;
import ru.ilyalagutin.workouttracker.exception.WorkoutAlreadyCompletedException;
import ru.ilyalagutin.workouttracker.repository.WorkoutPlanRepository;
import ru.ilyalagutin.workouttracker.repository.WorkoutRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @InjectMocks
    private WorkoutService workoutService;

    @Test
    void startCopiesExercisesFromPlanToWorkoutSets() {
        User user = new User("user@example.com", "password", UserRole.USER);
        WorkoutPlan workoutPlan = createWorkoutPlan(user);

        when(workoutPlanRepository.findByIdAndUserId(1L, user.getId()))
                .thenReturn(Optional.of(workoutPlan));
        when(workoutRepository.save(any(Workout.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        WorkoutResponse response = workoutService.start(1L, user);

        assertEquals("Chest workout", response.getName());
        assertEquals("IN_PROGRESS", response.getStatus());
        assertEquals(3, response.getSets().size());
        assertEquals(10, response.getSets().getFirst().getRepetitions());
        verify(workoutRepository).save(any(Workout.class));
    }

    @Test
    void updateSetChangesRepetitionsAndWeight() {
        User user = new User("user@example.com", "password", UserRole.USER);
        Workout workout = createWorkout(user);
        WorkoutSet workoutSet = workout.getSets().getFirst();
        ReflectionTestUtils.setField(workoutSet, "id", 1L);
        WorkoutSetUpdateRequest request = new WorkoutSetUpdateRequest();
        request.setRepetitions(8);
        request.setWeight(BigDecimal.valueOf(70));

        when(workoutRepository.findByIdAndUserId(1L, user.getId()))
                .thenReturn(Optional.of(workout));

        WorkoutResponse response = workoutService.updateSet(1L, 1L, request, user);

        assertEquals(8, response.getSets().getFirst().getRepetitions());
        assertEquals(BigDecimal.valueOf(70), response.getSets().getFirst().getWeight());
    }

    @Test
    void completeDoesNotAllowSecondCompletion() {
        User user = new User("user@example.com", "password", UserRole.USER);
        Workout workout = createWorkout(user);
        CompleteWorkoutRequest request = new CompleteWorkoutRequest();
        request.setComment("Good workout");

        when(workoutRepository.findByIdAndUserId(1L, user.getId()))
                .thenReturn(Optional.of(workout));

        workoutService.complete(1L, request, user);

        assertThrows(
                WorkoutAlreadyCompletedException.class,
                () -> workoutService.complete(1L, request, user));
    }

    private WorkoutPlan createWorkoutPlan(User user) {
        Exercise exercise = new Exercise(
                "Bench press",
                "Barbell press while lying on a bench",
                ExerciseCategory.STRENGTH);
        PlanExercise planExercise = new PlanExercise(exercise, 3, 10, BigDecimal.valueOf(60));
        WorkoutPlan workoutPlan = new WorkoutPlan(
                "Chest workout",
                "Training for chest muscles",
                LocalDateTime.of(2026, 10, 12, 18, 0),
                user);
        workoutPlan.addExercise(planExercise);

        return workoutPlan;
    }

    private Workout createWorkout(User user) {
        WorkoutPlan workoutPlan = createWorkoutPlan(user);
        Workout workout = new Workout(workoutPlan, user);
        workout.addSet(new WorkoutSet(
                workoutPlan.getExercises().getFirst().getExercise(),
                1,
                10,
                BigDecimal.valueOf(60)));

        return workout;
    }
}
