package com.example.interview_os.service;

import com.example.interview_os.dto.StoryRequestDTO;
import com.example.interview_os.dto.StoryResponseDTO;
import com.example.interview_os.entity.Story;
import com.example.interview_os.entity.User;
import com.example.interview_os.enums.StoryCategory;
import com.example.interview_os.mapper.StoryMapper;
import com.example.interview_os.repository.StoryRepository;
import com.example.interview_os.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoryServiceTest {

    @Mock
    private StoryRepository storyRepository;

    @Mock
    private StoryMapper storyMapper;

    @Mock
    private SecurityUtils securityUtils;

    @InjectMocks
    private StoryServiceImpl storyService;

    private User user;
    private User otherUser;
    private Story story;
    private StoryRequestDTO requestDTO;
    private StoryResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("darsh@example.com");

        otherUser = new User();
        otherUser.setId(2L);
        otherUser.setEmail("other@example.com");

        story = new Story();
        story.setId(1L);
        story.setUser(user);
        story.setTitle("Led team through deadline");
        story.setCategory(StoryCategory.LEADERSHIP);

        requestDTO = new StoryRequestDTO();
        requestDTO.setTitle("Led team through deadline");
        requestDTO.setCategory(StoryCategory.LEADERSHIP);
        requestDTO.setSituation("Situation");
        requestDTO.setTask("Task");
        requestDTO.setAction("Action");
        requestDTO.setResult("Result");

        responseDTO = new StoryResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUserId(1L);
        responseDTO.setTitle("Led team through deadline");
        responseDTO.setCategory(StoryCategory.LEADERSHIP);
    }

    @Test
    @DisplayName("Should create story linked to current user")
    void shouldCreateStoryLinkedToCurrentUser() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(user);
        when(storyMapper.toEntity(requestDTO)).thenReturn(story);
        when(storyRepository.save(story)).thenReturn(story);
        when(storyMapper.toResponseDTO(story)).thenReturn(responseDTO);

        // Act
        StoryResponseDTO result = storyService.createStory(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        verify(storyRepository, times(1)).save(story);
    }

    @Test
    @DisplayName("Should return only current user stories")
    void shouldReturnOnlyCurrentUserStories() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(user);
        when(storyRepository.findByUserId(1L)).thenReturn(List.of(story));
        when(storyMapper.toResponseDTO(story)).thenReturn(responseDTO);

        // Act
        List<StoryResponseDTO> result = storyService.getAllStories(null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
        verify(storyRepository, times(1)).findByUserId(1L);
        verify(storyRepository, never()).findByUserId(2L);
    }

    @Test
    @DisplayName("Should filter stories by category for current user")
    void shouldFilterStoriesByCategory() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(user);
        when(storyRepository.findByUserIdAndCategory(
                1L, StoryCategory.LEADERSHIP))
                .thenReturn(List.of(story));
        when(storyMapper.toResponseDTO(story)).thenReturn(responseDTO);

        // Act
        List<StoryResponseDTO> result =
                storyService.getAllStories(StoryCategory.LEADERSHIP);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(StoryCategory.LEADERSHIP,
                result.get(0).getCategory());
        verify(storyRepository, times(1))
                .findByUserIdAndCategory(1L, StoryCategory.LEADERSHIP);
    }

    @Test
    @DisplayName("Should never return other user stories")
    void shouldNeverReturnOtherUserStories() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(user);
        when(storyRepository.findByUserId(1L)).thenReturn(List.of());

        // Act
        List<StoryResponseDTO> result = storyService.getAllStories(null);

        // Assert
        assertEquals(0, result.size());
        verify(storyRepository, never()).findByUserId(2L);
    }
}