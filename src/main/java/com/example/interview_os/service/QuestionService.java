package com.example.interview_os.service;

import com.example.interview_os.dto.QuestionRequestDTO;
import com.example.interview_os.dto.QuestionResponseDTO;
import java.util.List;

public interface QuestionService {
    QuestionResponseDTO createQuestion(QuestionRequestDTO requestDTO);
    List<QuestionResponseDTO> getAllQuestions();
    QuestionResponseDTO getQuestionById(Long id);
    QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO requestDTO);
    void deleteQuestion(Long id);
}


