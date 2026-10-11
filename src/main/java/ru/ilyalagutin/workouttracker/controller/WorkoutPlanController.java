package ru.ilyalagutin.workouttracker.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutPlanRequest;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutPlanResponse;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.service.WorkoutPlanService;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutPlanResponse create(
            @Valid @RequestBody WorkoutPlanRequest request,
            @AuthenticationPrincipal User user) {
        return workoutPlanService.create(request, user);
    }

    @GetMapping
    public List<WorkoutPlanResponse> getAll(@AuthenticationPrincipal User user) {
        return workoutPlanService.getAll(user);
    }

    @GetMapping("/{id}")
    public WorkoutPlanResponse getById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return workoutPlanService.getById(id, user);
    }

    @PutMapping("/{id}")
    public WorkoutPlanResponse update(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutPlanRequest request,
            @AuthenticationPrincipal User user) {
        return workoutPlanService.update(id, request, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        workoutPlanService.delete(id, user);
    }
}
