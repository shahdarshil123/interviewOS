package com.example.interview_os.service;

import com.example.interview_os.dto.StoryRequestDTO;
import com.example.interview_os.dto.StoryResponseDTO;
import com.example.interview_os.enums.StoryCategory;
import java.util.List;

public interface StoryService {
    StoryResponseDTO createStory(StoryRequestDTO requestDTO);
    List<StoryResponseDTO> getAllStories(StoryCategory category);
}

