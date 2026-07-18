package com.example.interview_os.service;

import com.example.interview_os.dto.QuestionRequestDTO;
import com.example.interview_os.dto.QuestionResponseDTO;
import com.example.interview_os.entity.Question;
import com.example.interview_os.entity.User;
import com.example.interview_os.enums.Difficulty;
import com.example.interview_os.enums.Topic;
import com.example.interview_os.exception.ResourceNotFoundException;
import com.example.interview_os.mapper.QuestionMapper;
import com.example.interview_os.repository.QuestionRepository;
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
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private SecurityUtils securityUtils;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Question question;
    private QuestionRequestDTO requestDTO;
    private QuestionResponseDTO responseDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("darsh@example.com");
        user.setName("Darsh");

        question = new Question();
        question.setId(1L);
        question.setTitle("Two Sum");
        question.setDescription("Find two numbers that add up to target");
        question.setTopic(Topic.ARRAYS);
        question.setDifficulty(Difficulty.EASY);
        question.setCompanyTag("Google");
        question.setStatus(com.example.interview_os.enums.QuestionStatus.NOT_ATTEMPTED);
        question.setUser(user);

        requestDTO = new QuestionRequestDTO();
        requestDTO.setTitle("Two Sum");
        requestDTO.setDescription("Find two numbers that add up to target");
        requestDTO.setTopic(Topic.ARRAYS);
        requestDTO.setDifficulty(Difficulty.EASY);
        requestDTO.setCompanyTag("Google");

        responseDTO = new QuestionResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("Two Sum");
        responseDTO.setDescription("Find two numbers that add up to target");
        responseDTO.setTopic(Topic.ARRAYS);
        responseDTO.setDifficulty(Difficulty.EASY);
        responseDTO.setCompanyTag("Google");
        responseDTO.setStatus(com.example.interview_os.enums.QuestionStatus.NOT_ATTEMPTED);
        responseDTO.setCreatedAt(LocalDateTime.now());
        responseDTO.setUserId(1L);
    }

    private User createTestUser() {
        return user;
    }

    @Test
    @DisplayName("Should create question and return response DTO")
    void shouldCreateQuestion() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(createTestUser());
        when(questionMapper.toEntity(requestDTO)).thenReturn(question);
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.toResponseDTO(question)).thenReturn(responseDTO);

        // Act
        QuestionResponseDTO result = questionService.createQuestion(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Two Sum", result.getTitle());
        assertEquals(Topic.ARRAYS, result.getTopic());
        assertEquals(Difficulty.EASY, result.getDifficulty());
        verify(questionRepository, times(1)).save(any(Question.class));
    }

    @Test
    @DisplayName("Should return question when valid id provided")
    void shouldReturnQuestionById() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(createTestUser());
        when(questionRepository.findByIdAndUserId(1L, 1L))
                .thenReturn(Optional.of(question));
        when(questionMapper.toResponseDTO(question)).thenReturn(responseDTO);

        // Act
        QuestionResponseDTO result = questionService.getQuestionById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Two Sum", result.getTitle());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when question not found")
    void shouldThrowExceptionWhenQuestionNotFound() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(createTestUser());
        when(questionRepository.findByIdAndUserId(99L, 1L))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> questionService.getQuestionById(99L));

        verify(questionMapper, never()).toResponseDTO(any());
    }

    @Test
    @DisplayName("Should return list of questions")
    void shouldReturnAllQuestions() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(createTestUser());
        when(questionRepository.findAll(
                any(org.springframework.data.jpa.domain.Specification.class)))
                .thenReturn(List.of(question));
        when(questionMapper.toResponseDTO(question)).thenReturn(responseDTO);

        // Act
        List<QuestionResponseDTO> result =
                questionService.getAllQuestions(null, null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Two Sum", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Should update question and return updated response DTO")
    void shouldUpdateQuestion() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(createTestUser());
        when(questionRepository.findByIdAndUserId(1L, 1L))
                .thenReturn(Optional.of(question));
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.toResponseDTO(question)).thenReturn(responseDTO);

        // Act
        QuestionResponseDTO result =
                questionService.updateQuestion(1L, requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Two Sum", result.getTitle());
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existing question")
    void shouldThrowExceptionWhenDeletingNonExistingQuestion() {
        // Arrange
        when(securityUtils.getCurrentUser()).thenReturn(createTestUser());
        when(questionRepository.findByIdAndUserId(99L, 1L))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> questionService.deleteQuestion(99L));

        verify(questionRepository, never()).deleteById(any());
    }
}