package com.example.interview_os.service;

import com.example.interview_os.dto.StoryRequestDTO;
import com.example.interview_os.dto.StoryResponseDTO;
import com.example.interview_os.entity.Story;
import com.example.interview_os.entity.User;
import com.example.interview_os.enums.StoryCategory;
import com.example.interview_os.mapper.StoryMapper;
import com.example.interview_os.repository.StoryRepository;
import com.example.interview_os.security.SecurityUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    private final SecurityUtils securityUtils;

    public StoryServiceImpl(StoryRepository storyRepository,
                            StoryMapper storyMapper,
                            SecurityUtils securityUtils) {
        this.storyRepository = storyRepository;
        this.storyMapper = storyMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public StoryResponseDTO createStory(StoryRequestDTO requestDTO) {
        User currentUser = securityUtils.getCurrentUser();
        Story story = storyMapper.toEntity(requestDTO);
        story.setUser(currentUser);
        Story saved = storyRepository.save(story);
        return storyMapper.toResponseDTO(saved);
    }

    @Override
    public List<StoryResponseDTO> getAllStories(StoryCategory category) {
        User currentUser = securityUtils.getCurrentUser();

        List<Story> stories = category != null
                ? storyRepository.findByUserIdAndCategory(
                currentUser.getId(), category)
                : storyRepository.findByUserId(currentUser.getId());

        return stories.stream()
                .map(storyMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}