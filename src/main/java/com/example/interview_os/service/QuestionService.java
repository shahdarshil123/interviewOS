package com.example.interview_os.service;

import com.example.interview_os.dto.QuestionRequestDTO;
import com.example.interview_os.dto.QuestionResponseDTO;
import com.example.interview_os.enums.Difficulty;
import com.example.interview_os.enums.Topic;

import java.util.List;

public interface QuestionService {
    QuestionResponseDTO createQuestion(QuestionRequestDTO requestDTO);
//    List<QuestionResponseDTO> getAllQuestions();

    List<QuestionResponseDTO> getAllQuestions(Topic topic, Difficulty difficulty, String status, String companyTag);

    QuestionResponseDTO getQuestionById(Long id);
    QuestionResponseDTO updateQuestion(Long id, QuestionRequestDTO requestDTO);
    void deleteQuestion(Long id);
}


