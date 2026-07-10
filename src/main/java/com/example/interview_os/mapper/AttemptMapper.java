package com.example.interview_os.mapper;

import com.example.interview_os.dto.AttemptRequestDTO;
import com.example.interview_os.dto.AttemptResponseDTO;
import com.example.interview_os.entity.QuestionAttempt;
import org.springframework.stereotype.Component;

@Component
public class AttemptMapper {

    public QuestionAttempt toEntity(AttemptRequestDTO requestDTO) {
        QuestionAttempt attempt = new QuestionAttempt();
        attempt.setStatus(requestDTO.getStatus());
        attempt.setTimeTaken(requestDTO.getTimeTaken());
        attempt.setApproach(requestDTO.getApproch());
        attempt.setMistakes(requestDTO.getMistakes());
        attempt.setNotes(requestDTO.getNotes());
        attempt.setConfidenceScore(requestDTO.getConfidenceScore());
        return attempt;
    }

    public AttemptResponseDTO toResponseDTO(QuestionAttempt attempt) {
        AttemptResponseDTO dto = new AttemptResponseDTO();
        dto.setId(attempt.getId());
        dto.setQuestionId(attempt.getQuestion().getId());
        dto.setStatus(attempt.getStatus());
        dto.setTimeTaken(attempt.getTimeTaken());
        dto.setApproach(attempt.getApproach());
        dto.setMistakes(attempt.getMistakes());
        dto.setNotes(attempt.getNotes());
        dto.setConfidenceScore(attempt.getConfidenceScore());
        dto.setAttemptedAt(attempt.getAttemptedAt());
        return dto;
    }
}
