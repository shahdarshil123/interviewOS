package com.example.interview_os.mapper;

import com.example.interview_os.dto.FeedbackRequestDTO;
import com.example.interview_os.dto.FeedbackResponseDTO;
import com.example.interview_os.entity.InterviewFeedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public InterviewFeedback toEntity(FeedbackRequestDTO requestDTO) {
        InterviewFeedback feedback = new InterviewFeedback();
        feedback.setStrengths(requestDTO.getStrengths());
        feedback.setWeaknesses(requestDTO.getWeaknesses());
        feedback.setTechnicalScore(requestDTO.getTechnicalScore());
        feedback.setCommunicationScore(requestDTO.getCommunicationScore());
        feedback.setNextSteps(requestDTO.getNextSteps());
        return feedback;
    }

    public FeedbackResponseDTO toResponseDTO(InterviewFeedback feedback) {
        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setId(feedback.getId());
        dto.setInterviewId(feedback.getInterview().getId());
        dto.setStrengths(feedback.getStrengths());
        dto.setWeaknesses(feedback.getWeaknesses());
        dto.setTechnicalScore(feedback.getTechnicalScore());
        dto.setCommunicationScore(feedback.getCommunicationScore());
        dto.setNextSteps(feedback.getNextSteps());
        dto.setCreatedAt(feedback.getCreatedAt());
        return dto;
    }
}