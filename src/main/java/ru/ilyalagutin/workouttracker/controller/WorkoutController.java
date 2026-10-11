package ru.ilyalagutin.workouttracker.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ilyalagutin.workouttracker.dto.PageResponse;
import ru.ilyalagutin.workouttracker.dto.workout.CompleteWorkoutRequest;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutResponse;
import ru.ilyalagutin.workouttracker.dto.workout.WorkoutSetUpdateRequest;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.service.WorkoutService;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/from-plan/{planId}")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutResponse start(@PathVariable Long planId, @AuthenticationPrincipal User user) {
        return workoutService.start(planId, user);
    }

    @GetMapping("/{id}")
    public WorkoutResponse getById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return workoutService.getById(id, user);
    }

    @PatchMapping("/{workoutId}/sets/{setId}")
    public WorkoutResponse updateSet(
            @PathVariable Long workoutId,
            @PathVariable Long setId,
            @Valid @RequestBody WorkoutSetUpdateRequest request,
            @AuthenticationPrincipal User user) {
        return workoutService.updateSet(workoutId, setId, request, user);
    }

    @PostMapping("/{id}/complete")
    public WorkoutResponse complete(
            @PathVariable Long id,
            @Valid @RequestBody CompleteWorkoutRequest request,
            @AuthenticationPrincipal User user) {
        return workoutService.complete(id, request, user);
    }

    @GetMapping("/history")
    public PageResponse<WorkoutResponse> getHistory(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size,
            @AuthenticationPrincipal User user) {
        return workoutService.getHistory(name, page, size, user);
    }
}
