package com.example.interview_os.controller;

import com.example.interview_os.dto.QuestionRequestDTO;
import com.example.interview_os.dto.QuestionResponseDTO;
import com.example.interview_os.entity.Question;
import com.example.interview_os.enums.Difficulty;
import com.example.interview_os.enums.Topic;
import com.example.interview_os.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion(@Valid @RequestBody QuestionRequestDTO requestDTO) {
        QuestionResponseDTO response = questionService.createQuestion(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getQuestionById(id));
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestion(
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String companyTag
    ) {
        Topic topicEnum = topic != null ? Topic.valueOf(topic.toUpperCase()) : null;
        Difficulty difficultyEnum = difficulty != null ?
                Difficulty.valueOf(difficulty.toUpperCase()) : null;

        return ResponseEntity.ok(questionService.getAllQuestions(topicEnum, difficultyEnum, status, companyTag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequestDTO requestDTO) {
        return ResponseEntity.ok(questionService.updateQuestion(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
