package ru.ilyalagutin.workouttracker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilyalagutin.workouttracker.dto.PageResponse;
import ru.ilyalagutin.workouttracker.dto.workout.CompleteWorkoutRequest;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutResponse;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutSetResponse;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutSetUpdateRequest;
import ru.ilyalagutin.workouttracker.entity.PlanExercise;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.entity.Workout;
import ru.ilyalagutin.workouttracker.entity.WorkoutSet;
import ru.ilyalagutin.workouttracker.entity.WorkoutStatus;
import ru.ilyalagutin.workouttracker.entity.WorkoutPlan;
import ru.ilyalagutin.workouttracker.exception.WorkoutAlreadyCompletedException;
import ru.ilyalagutin.workouttracker.exception.WorkoutNotFoundException;
import ru.ilyalagutin.workouttracker.exception.WorkoutSetNotFoundException;
import ru.ilyalagutin.workouttracker.exception.WorkoutPlanNotFoundException;
import ru.ilyalagutin.workouttracker.repository.WorkoutPlanRepository;
import ru.ilyalagutin.workouttracker.repository.WorkoutRepository;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    public WorkoutService(
            WorkoutRepository workoutRepository,
            WorkoutPlanRepository workoutPlanRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Transactional
    public WorkoutResponse start(Long planId, User user) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findByIdAndUserId(planId, user.getId())
                .orElseThrow(() -> new WorkoutPlanNotFoundException(planId));
        Workout workout = new Workout(workoutPlan, user);

        for (PlanExercise planExercise : workoutPlan.getExercises()) {
            for (int setNumber = 1; setNumber <= planExercise.getSetCount(); setNumber++) {
                workout.addSet(new WorkoutSet(
                        planExercise.getExercise(),
                        setNumber,
                        planExercise.getRepetitions(),
                        planExercise.getWeight()));
            }
        }

        workoutRepository.save(workout);

        return toResponse(workout);
    }

    @Transactional(readOnly = true)
    public WorkoutResponse getById(Long id, User user) {
        return toResponse(findUserWorkout(id, user));
    }

    @Transactional
    public WorkoutResponse updateSet(
            Long workoutId,
            Long setId,
            WorkoutSetUpdateRequest request,
            User user) {
        Workout workout = findUserWorkout(workoutId, user);
        checkInProgress(workout);
        WorkoutSet workoutSet = workout.getSets().stream()
                .filter(set -> set.getId().equals(setId))
                .findFirst()
                .orElseThrow(() -> new WorkoutSetNotFoundException(setId));
        workoutSet.update(request.getRepetitions(), request.getWeight());

        return toResponse(workout);
    }

    @Transactional
    public WorkoutResponse complete(Long id, CompleteWorkoutRequest request, User user) {
        Workout workout = findUserWorkout(id, user);
        checkInProgress(workout);
        workout.complete(request.getComment());

        return toResponse(workout);
    }

    @Transactional(readOnly = true)
    public PageResponse<WorkoutResponse> getHistory(
            String name,
            int page,
            int size,
            User user) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Workout> workouts;

        if (name == null || name.isBlank()) {
            workouts = workoutRepository.findByUserIdAndStatusOrderByCompletedAtDesc(
                    user.getId(),
                    WorkoutStatus.COMPLETED,
                    pageable);
        } else {
            workouts = workoutRepository.findByUserIdAndNameContainingIgnoreCaseAndStatusOrderByCompletedAtDesc(
                    user.getId(),
                    name,
                    WorkoutStatus.COMPLETED,
                    pageable);
        }

        List<WorkoutResponse> content = workouts.getContent().stream()
                .map(this::toResponse)
                .toList();

        return new PageResponse<>(
                content,
                workouts.getNumber(),
                workouts.getSize(),
                workouts.getTotalElements(),
                workouts.getTotalPages());
    }

    private Workout findUserWorkout(Long id, User user) {
        return workoutRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new WorkoutNotFoundException(id));
    }

    private void checkInProgress(Workout workout) {
        if (workout.getStatus() == WorkoutStatus.COMPLETED) {
            throw new WorkoutAlreadyCompletedException();
        }
    }

    private WorkoutResponse toResponse(Workout workout) {
        List<WorkoutSetResponse> sets = workout.getSets().stream()
                .map(set -> new WorkoutSetResponse(
                        set.getId(),
                        set.getExercise().getId(),
                        set.getExercise().getName(),
                        set.getSetNumber(),
                        set.getRepetitions(),
                        set.getWeight()))
                .toList();

        return new WorkoutResponse(
                workout.getId(),
                workout.getWorkoutPlan().getId(),
                workout.getName(),
                workout.getComment(),
                workout.getStatus().name(),
                workout.getStartedAt(),
                workout.getCompletedAt(),
                sets);
    }
}
