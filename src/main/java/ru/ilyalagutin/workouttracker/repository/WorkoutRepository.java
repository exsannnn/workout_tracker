package ru.ilyalagutin.workouttracker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilyalagutin.workouttracker.entity.Workout;
import ru.ilyalagutin.workouttracker.entity.WorkoutStatus;

import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<Workout> findByIdAndUserId(Long id, Long userId);

    Page<Workout> findByUserIdAndStatusOrderByCompletedAtDesc(
            Long userId,
            WorkoutStatus status,
            Pageable pageable);

    Page<Workout> findByUserIdAndNameContainingIgnoreCaseAndStatusOrderByCompletedAtDesc(
            Long userId,
            String name,
            WorkoutStatus status,
            Pageable pageable);
}
