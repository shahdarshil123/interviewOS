package com.example.interview_os.controller;

import com.example.interview_os.dto.analytics.TopicSummaryDTO;
import com.example.interview_os.dto.analytics.WeakTopicDTO;
import com.example.interview_os.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService){
        this.analyticsService = analyticsService;
    }

    @GetMapping("/weak-topics")
    public ResponseEntity<List<WeakTopicDTO>> getWeakTopics(){
        return ResponseEntity.ok(analyticsService.getWeakTopics());
    }

    @GetMapping("/topic-summary")
    public ResponseEntity<List<TopicSummaryDTO>> getTopicSummary() {
        return ResponseEntity.ok(analyticsService.getTopicSummary());
    }
}

