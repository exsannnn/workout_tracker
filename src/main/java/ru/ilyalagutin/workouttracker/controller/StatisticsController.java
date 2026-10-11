package ru.ilyalagutin.workouttracker.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilyalagutin.workouttracker.dto.statistics.StatisticsResponse;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.service.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public StatisticsResponse getStatistics(@AuthenticationPrincipal User user) {
        return statisticsService.getForUser(user);
    }
}
