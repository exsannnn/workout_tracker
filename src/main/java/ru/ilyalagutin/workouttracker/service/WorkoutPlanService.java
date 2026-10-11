package ru.ilyalagutin.workouttracker.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilyalagutin.workouttracker.dto.workout.PlanExerciseRequest;
import ru.ilyalagutin.workouttracker.dto.workout.PlanExerciseResponse;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutPlanRequest;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutPlanResponse;
import ru.ilyalagutin.workouttracker.entity.Exercise;
import ru.ilyalagutin.workouttracker.entity.PlanExercise;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.entity.WorkoutPlan;
import ru.ilyalagutin.workouttracker.exception.ExerciseNotFoundException;
import ru.ilyalagutin.workouttracker.exception.WorkoutPlanNotFoundException;
import ru.ilyalagutin.workouttracker.repository.ExerciseRepository;
import ru.ilyalagutin.workouttracker.repository.WorkoutPlanRepository;

import java.util.List;

@Service
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutPlanService(
            WorkoutPlanRepository workoutPlanRepository,
            ExerciseRepository exerciseRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional
    public WorkoutPlanResponse create(WorkoutPlanRequest request, User user) {
        WorkoutPlan workoutPlan = new WorkoutPlan(
                request.getName(),
                request.getDescription(),
                request.getPlannedAt(),
                user);
        addExercises(workoutPlan, request.getExercises());
        workoutPlanRepository.save(workoutPlan);

        return toResponse(workoutPlan);
    }

    @Transactional(readOnly = true)
    public List<WorkoutPlanResponse> getAll(User user) {
        return workoutPlanRepository.findAllByUserIdOrderByPlannedAtDesc(user.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public WorkoutPlanResponse getById(Long id, User user) {
        WorkoutPlan workoutPlan = findUserPlan(id, user);

        return toResponse(workoutPlan);
    }

    @Transactional
    public WorkoutPlanResponse update(Long id, WorkoutPlanRequest request, User user) {
        WorkoutPlan workoutPlan = findUserPlan(id, user);
        workoutPlan.update(request.getName(), request.getDescription(), request.getPlannedAt());
        workoutPlan.clearExercises();
        addExercises(workoutPlan, request.getExercises());

        return toResponse(workoutPlan);
    }

    @Transactional
    public void delete(Long id, User user) {
        WorkoutPlan workoutPlan = findUserPlan(id, user);
        workoutPlanRepository.delete(workoutPlan);
    }

    private void addExercises(WorkoutPlan workoutPlan, List<PlanExerciseRequest> requests) {
        for (PlanExerciseRequest request : requests) {
            Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                    .orElseThrow(() -> new ExerciseNotFoundException(request.getExerciseId()));
            PlanExercise planExercise = new PlanExercise(
                    exercise,
                    request.getSetCount(),
                    request.getRepetitions(),
                    request.getWeight());
            workoutPlan.addExercise(planExercise);
        }
    }

    private WorkoutPlan findUserPlan(Long id, User user) {
        return workoutPlanRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new WorkoutPlanNotFoundException(id));
    }

    private WorkoutPlanResponse toResponse(WorkoutPlan workoutPlan) {
        List<PlanExerciseResponse> exercises = workoutPlan.getExercises().stream()
                .map(planExercise -> new PlanExerciseResponse(
                        planExercise.getExercise().getId(),
                        planExercise.getExercise().getName(),
                        planExercise.getSetCount(),
                        planExercise.getRepetitions(),
                        planExercise.getWeight()))
                .toList();

        return new WorkoutPlanResponse(
                workoutPlan.getId(),
                workoutPlan.getName(),
                workoutPlan.getDescription(),
                workoutPlan.getPlannedAt(),
                exercises);
    }
}
