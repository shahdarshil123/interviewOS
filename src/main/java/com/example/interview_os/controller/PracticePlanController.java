package com.example.interview_os.controller;

import com.example.interview_os.dto.dashboard.PracticePlanDTO;
import com.example.interview_os.service.PracticePlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/practice-plan")
public class PracticePlanController {

    private final PracticePlanService practicePlanService;

    public PracticePlanController(PracticePlanService practicePlanService) {
        this.practicePlanService = practicePlanService;
    }

    @GetMapping("/today")
    public ResponseEntity<PracticePlanDTO> getTodaysPracticePlan() {
        return ResponseEntity.ok(practicePlanService.getTodaysPracticePlan());
    }
}