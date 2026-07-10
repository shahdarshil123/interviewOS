package com.example.interview_os.service;

import com.example.interview_os.dto.AttemptRequestDTO;
import com.example.interview_os.dto.AttemptResponseDTO;

import java.util.List;

public interface AttemptService {
    AttemptResponseDTO createAttempt(Long questionId, AttemptRequestDTO requestDTO);

    List<AttemptResponseDTO> getAttemptsByQuestionId(Long questionId);
}


