package com.example.interview_os.controller;

import com.example.interview_os.dto.dashboard.ReadinessDTO;
import com.example.interview_os.dto.dashboard.TopicBreakdownDTO;
import com.example.interview_os.dto.dashboard.UpcomingInterviewDTO;
import com.example.interview_os.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/readiness")
    public ResponseEntity<ReadinessDTO> getReadiness() {
        return ResponseEntity.ok(dashboardService.getReadiness());
    }

    @GetMapping("/topic-breakdown")
    public ResponseEntity<List<TopicBreakdownDTO>> getTopicBreakdown() {
        return ResponseEntity.ok(dashboardService.getTopicBreakdown());
    }

    @GetMapping("/upcoming-interviews")
    public ResponseEntity<List<UpcomingInterviewDTO>> getUpcomingInterviews() {
        return ResponseEntity.ok(dashboardService.getUpcomingInterviews());
    }
}