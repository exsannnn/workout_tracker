package ru.ilyalagutin.workouttracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ilyalagutin.workouttracker.dto.statistics.StatisticsResponse;
import ru.ilyalagutin.workouttracker.entity.Exercise;
import ru.ilyalagutin.workouttracker.entity.ExerciseCategory;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.entity.UserRole;
import ru.ilyalagutin.workouttracker.entity.Workout;
import ru.ilyalagutin.workouttracker.entity.WorkoutPlan;
import ru.ilyalagutin.workouttracker.entity.WorkoutSet;
import ru.ilyalagutin.workouttracker.entity.WorkoutStatus;
import ru.ilyalagutin.workouttracker.repository.WorkoutRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void getForUserCalculatesWorkoutProgress() {
        User user = new User("user@example.com", "password", UserRole.USER);
        Workout workout = createWorkout(user);
        when(workoutRepository.findAllByUserIdAndStatusOrderByCompletedAtDesc(
                user.getId(),
                WorkoutStatus.COMPLETED)).thenReturn(List.of(workout));

        StatisticsResponse response = statisticsService.getForUser(user);

        assertEquals(1, response.getCompletedWorkouts());
        assertEquals(2, response.getTotalSets());
        assertEquals(20, response.getTotalRepetitions());
        assertEquals(BigDecimal.valueOf(1200), response.getTotalVolume());
        assertEquals(BigDecimal.valueOf(60), response.getMaxWeight());
    }

    @Test
    void getForUserReturnsZeroValuesWithoutWorkouts() {
        User user = new User("user@example.com", "password", UserRole.USER);
        when(workoutRepository.findAllByUserIdAndStatusOrderByCompletedAtDesc(
                user.getId(),
                WorkoutStatus.COMPLETED)).thenReturn(List.of());

        StatisticsResponse response = statisticsService.getForUser(user);

        assertEquals(0, response.getCompletedWorkouts());
        assertEquals(0, response.getTotalSets());
        assertEquals(BigDecimal.ZERO, response.getTotalVolume());
    }

    private Workout createWorkout(User user) {
        Exercise exercise = new Exercise(
                "Bench press",
                "Barbell press while lying on a bench",
                ExerciseCategory.STRENGTH);
        WorkoutPlan workoutPlan = new WorkoutPlan(
                "Chest workout",
                "Training for chest muscles",
                LocalDateTime.of(2026, 10, 12, 18, 0),
                user);
        Workout workout = new Workout(workoutPlan, user);
        workout.addSet(new WorkoutSet(exercise, 1, 10, BigDecimal.valueOf(60)));
        workout.addSet(new WorkoutSet(exercise, 2, 10, BigDecimal.valueOf(60)));
        workout.complete("Good workout");

        return workout;
    }
}
