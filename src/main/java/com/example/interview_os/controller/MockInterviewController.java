package com.example.interview_os.controller;

import com.example.interview_os.dto.FeedbackRequestDTO;
import com.example.interview_os.dto.FeedbackResponseDTO;
import com.example.interview_os.dto.MockInterviewRequestDTO;
import com.example.interview_os.dto.MockInterviewResponseDTO;
import com.example.interview_os.enums.InterviewStatus;
import com.example.interview_os.service.MockInterviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mock-interviews")
public class MockInterviewController {

    private final MockInterviewService interviewService;

    public MockInterviewController(MockInterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<MockInterviewResponseDTO> createInterview(
            @Valid @RequestBody MockInterviewRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(interviewService.createInterview(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<MockInterviewResponseDTO>> getAllInterviews(
            @RequestParam(required = false) InterviewStatus status) {
        return ResponseEntity.ok(
                interviewService.getAllInterviews(status));
    }

    @PostMapping("/{id}/feedback")
    public ResponseEntity<FeedbackResponseDTO> addFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(interviewService.addFeedback(id, requestDTO));
    }


}