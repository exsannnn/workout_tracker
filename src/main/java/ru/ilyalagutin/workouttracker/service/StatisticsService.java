package ru.ilyalagutin.workouttracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilyalagutin.workouttracker.dto.statistics.StatisticsResponse;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.entity.Workout;
import ru.ilyalagutin.workouttracker.entity.WorkoutSet;
import ru.ilyalagutin.workouttracker.entity.WorkoutStatus;
import ru.ilyalagutin.workouttracker.repository.WorkoutRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatisticsService {

    private final WorkoutRepository workoutRepository;

    public StatisticsService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Transactional(readOnly = true)
    public StatisticsResponse getForUser(User user) {
        List<Workout> workouts = workoutRepository
                .findAllByUserIdAndStatusOrderByCompletedAtDesc(user.getId(), WorkoutStatus.COMPLETED);
        long totalSets = 0;
        long totalRepetitions = 0;
        BigDecimal totalVolume = BigDecimal.ZERO;
        BigDecimal maxWeight = BigDecimal.ZERO;

        for (Workout workout : workouts) {
            for (WorkoutSet set : workout.getSets()) {
                totalSets++;
                totalRepetitions += set.getRepetitions();
                totalVolume = totalVolume.add(
                        set.getWeight().multiply(BigDecimal.valueOf(set.getRepetitions())));

                if (set.getWeight().compareTo(maxWeight) > 0) {
                    maxWeight = set.getWeight();
                }
            }
        }

        return new StatisticsResponse(
                workouts.size(),
                totalSets,
                totalRepetitions,
                totalVolume,
                maxWeight,
                workouts.isEmpty() ? null : workouts.getFirst().getCompletedAt());
    }
}
