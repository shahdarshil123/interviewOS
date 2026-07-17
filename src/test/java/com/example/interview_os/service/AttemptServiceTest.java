package com.example.interview_os.service;

import com.example.interview_os.dto.AttemptRequestDTO;
import com.example.interview_os.dto.AttemptResponseDTO;
import com.example.interview_os.entity.Question;
import com.example.interview_os.entity.QuestionAttempt;
import com.example.interview_os.enums.AttemptStatus;
import com.example.interview_os.enums.Difficulty;
import com.example.interview_os.enums.QuestionStatus;
import com.example.interview_os.enums.Topic;
import com.example.interview_os.exception.ResourceNotFoundException;
import com.example.interview_os.mapper.AttemptMapper;
import com.example.interview_os.repository.QuestionAttemptRepository;
import com.example.interview_os.repository.QuestionRepository;
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
class AttemptServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionAttemptRepository attemptRepository;

    @Mock
    private AttemptMapper attemptMapper;

    @InjectMocks
    private AttemptServiceImpl attemptService;

    private Question question;
    private QuestionAttempt attempt;
    private AttemptRequestDTO requestDTO;
    private AttemptResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        question = new Question();
        question.setId(1L);
        question.setTitle("Two Sum");
        question.setTopic(Topic.ARRAYS);
        question.setDifficulty(Difficulty.EASY);
        question.setStatus(QuestionStatus.NOT_ATTEMPTED);
        question.setConfidenceScore(0);

        requestDTO = new AttemptRequestDTO();
        requestDTO.setStatus(AttemptStatus.SOLVED);
        requestDTO.setTimeTaken(20);
        requestDTO.setApproach("Two pointer");
        requestDTO.setMistakes("None");
        requestDTO.setNotes("Clean solution");
        requestDTO.setConfidenceScore(9);

        attempt = new QuestionAttempt();
        attempt.setId(1L);
        attempt.setQuestion(question);
        attempt.setStatus(AttemptStatus.SOLVED);
        attempt.setTimeTaken(20);
        attempt.setConfidenceScore(9);
        attempt.setAttemptedAt(LocalDateTime.now());

        responseDTO = new AttemptResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setQuestionId(1L);
        responseDTO.setStatus(AttemptStatus.SOLVED);
        responseDTO.setTimeTaken(20);
        responseDTO.setConfidenceScore(9);
        responseDTO.setAttemptedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should create attempt and update question status")
    void shouldCreateAttemptAndUpdateQuestionStatus() {
        // Arrange
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(attemptMapper.toEntity(requestDTO)).thenReturn(attempt);
        when(attemptRepository.save(attempt)).thenReturn(attempt);
        when(attemptMapper.toResponseDTO(attempt)).thenReturn(responseDTO);
        when(questionRepository.save(question)).thenReturn(question);

        // Act
        AttemptResponseDTO result = attemptService.createAttempt(1L, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(AttemptStatus.SOLVED, result.getStatus());
        assertEquals(9, result.getConfidenceScore());
        verify(questionRepository, times(1)).save(question);
        verify(attemptRepository, times(1)).save(attempt);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when question not found on create attempt")
    void shouldThrowExceptionWhenQuestionNotFoundOnCreate() {
        // Arrange
        when(questionRepository.findById(99L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> attemptService.createAttempt(99L, requestDTO));

        verify(attemptRepository, never()).save(any());
        verify(questionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return attempts for valid question")
    void shouldReturnAttemptsForValidQuestion() {
        // Arrange
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(attemptRepository.findByQuestionId(1L)).thenReturn(List.of(attempt));
        when(attemptMapper.toResponseDTO(attempt)).thenReturn(responseDTO);

        // Act
        List<AttemptResponseDTO> result = attemptService.getAttemptsByQuestionId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(AttemptStatus.SOLVED, result.get(0).getStatus());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when question not found on get attempts")
    void shouldThrowExceptionWhenQuestionNotFoundOnGetAttempts() {
        // Arrange
        when(questionRepository.findById(99L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> attemptService.getAttemptsByQuestionId(99L));

        verify(attemptRepository, never()).findByQuestionId(any());
    }
}