package com.example.interview_os.controller;

import com.example.interview_os.dto.AttemptRequestDTO;
import com.example.interview_os.dto.AttemptResponseDTO;
import com.example.interview_os.service.AttemptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions/{questionId}/attempts")
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping
    public ResponseEntity<AttemptResponseDTO> createAttempt(
            @PathVariable Long questionId,
            @Valid @RequestBody AttemptRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(attemptService.createAttempt(questionId, requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<AttemptResponseDTO>> getAttemptsByQuestionId(
            @PathVariable Long questionId) {
        return ResponseEntity.ok(attemptService.getAttemptsByQuestionId(questionId));
    }
}