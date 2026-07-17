package com.example.interview_os.controller;

import com.example.interview_os.dto.StoryRequestDTO;
import com.example.interview_os.dto.StoryResponseDTO;
import com.example.interview_os.enums.StoryCategory;
import com.example.interview_os.service.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stories")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping
    public ResponseEntity<StoryResponseDTO> createStory(
            @Valid @RequestBody StoryRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storyService.createStory(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<StoryResponseDTO>> getAllStories(
            @RequestParam(required = false) StoryCategory category) {
        return ResponseEntity.ok(storyService.getAllStories(category));
    }
}