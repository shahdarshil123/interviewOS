package com.example.interview_os.service;

import com.example.interview_os.dto.MockInterviewRequestDTO;
import com.example.interview_os.dto.MockInterviewResponseDTO;
import com.example.interview_os.entity.MockInterview;
import com.example.interview_os.entity.User;
import com.example.interview_os.enums.InterviewStatus;
import com.example.interview_os.enums.InterviewType;
import com.example.interview_os.exception.ResourceNotFoundException;
import com.example.interview_os.mapper.FeedbackMapper;
import com.example.interview_os.mapper.MockInterviewMapper;
import com.example.interview_os.repository.FeedbackRepository;
import com.example.interview_os.repository.MockInterviewRepository;
import com.example.interview_os.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockInterviewServiceTest {

    @Mock
    private MockInterviewRepository interviewRepository;

    @Mock
    private MockInterviewMapper interviewMapper;

    @Mock
    private SecurityUtils securityUtils;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private FeedbackMapper feedbackMapper;

    @InjectMocks
    private MockInterviewServiceImpl interviewService;

    private User user;
    private User otherUser;
    private MockInterview interview;
    private MockInterviewRequestDTO requestDTO;
    private MockInterviewResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("darsh@example.com");

        otherUser = new User();
        otherUser.setId(2L);
        otherUser.setEmail("other@example.com");

        interview = new MockInterview();
        interview.setId(1L);
        interview.setUser(user);
        interview.setCompany("Google");
        interview.setType(InterviewType.TECHNICAL);
        interview.setStatus(InterviewStatus.SCHEDULED);
        interview.setScheduledAt(LocalDateTime.now().plusDays(1));

        requestDTO = new MockInterviewRequestDTO();
        requestDTO.setCompany("Google");
        requestDTO.setType(InterviewType.TECHNICAL);
        requestDTO.setScheduledAt(LocalDateTime.now().plusDays(1));

        responseDTO = new MockInterviewResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setUserId(1L);
        responseDTO.setCompany("Google");
        responseDTO.setStatus(InterviewStatus.SCHEDULED);
    }

    @Test
    @DisplayName("Should create interview linked to current user")
    void shouldCreateInterviewLinkedToCurrentUser() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(user);
        when(interviewMapper.toEntity(requestDTO)).thenReturn(interview);
        when(interviewRepository.save(interview)).thenReturn(interview);
        when(interviewMapper.toResponseDTO(interview)).thenReturn(responseDTO);

        // Act
        MockInterviewResponseDTO result =
                interviewService.createInterview(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        verify(interviewRepository, times(1)).save(interview);
    }

    @Test
    @DisplayName("Should return only current user interviews")
    void shouldReturnOnlyCurrentUserInterviews() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(user);
        when(interviewRepository.findByUserId(1L))
                .thenReturn(List.of(interview));
        when(interviewMapper.toResponseDTO(interview)).thenReturn(responseDTO);

        // Act
        List<MockInterviewResponseDTO> result =
                interviewService.getAllInterviews(null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
        verify(interviewRepository, times(1)).findByUserId(1L);
        verify(interviewRepository, never()).findByUserId(2L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when accessing other user interview")
    void shouldThrowExceptionWhenAccessingOtherUserInterview() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(otherUser);
        when(interviewRepository.findByIdAndUserId(1L, 2L))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> interviewService.addFeedback(1L, null));
    }

    @Test
    @DisplayName("Should return interviews filtered by status")
    void shouldReturnInterviewsFilteredByStatus() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(user);
        when(interviewRepository.findByUserIdAndStatus(
                1L, InterviewStatus.SCHEDULED))
                .thenReturn(List.of(interview));
        when(interviewMapper.toResponseDTO(interview)).thenReturn(responseDTO);

        // Act
        List<MockInterviewResponseDTO> result =
                interviewService.getAllInterviews(InterviewStatus.SCHEDULED);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(InterviewStatus.SCHEDULED,
                result.get(0).getStatus());
    }
}