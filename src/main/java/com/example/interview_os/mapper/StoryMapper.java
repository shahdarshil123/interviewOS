package com.example.interview_os.mapper;

import com.example.interview_os.dto.StoryRequestDTO;
import com.example.interview_os.dto.StoryResponseDTO;
import com.example.interview_os.entity.Story;
import org.springframework.stereotype.Component;

@Component
public class StoryMapper {

    public Story toEntity(StoryRequestDTO requestDTO) {
        Story story = new Story();
        story.setTitle(requestDTO.getTitle());
        story.setSituation(requestDTO.getSituation());
        story.setTask(requestDTO.getTask());
        story.setAction(requestDTO.getAction());
        story.setResult(requestDTO.getResult());
        story.setCategory(requestDTO.getCategory());
        story.setTags(requestDTO.getTags());
        return story;
    }

    public StoryResponseDTO toResponseDTO(Story story) {
        StoryResponseDTO dto = new StoryResponseDTO();
        dto.setId(story.getId());
        dto.setUserId(story.getUser().getId());
        dto.setTitle(story.getTitle());
        dto.setSituation(story.getSituation());
        dto.setTask(story.getTask());
        dto.setAction(story.getAction());
        dto.setResult(story.getResult());
        dto.setCategory(story.getCategory());
        dto.setTags(story.getTags());
        dto.setCreatedAt(story.getCreatedAt());
        return dto;
    }
}