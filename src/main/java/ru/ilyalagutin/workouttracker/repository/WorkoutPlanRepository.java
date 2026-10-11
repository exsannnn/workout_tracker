package ru.ilyalagutin.workouttracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilyalagutin.workouttracker.entity.WorkoutPlan;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    Optional<WorkoutPlan> findByIdAndUserId(Long id, Long userId);

    List<WorkoutPlan> findAllByUserIdOrderByPlannedAtDesc(Long userId);
}
