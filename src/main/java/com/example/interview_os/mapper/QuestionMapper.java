package com.example.interview_os.mapper;

import com.example.interview_os.dto.QuestionRequestDTO;
import com.example.interview_os.dto.QuestionResponseDTO;
import com.example.interview_os.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {
    public Question toEntity(QuestionRequestDTO requestDTO){
        Question question = new Question();
        question.setTitle(requestDTO.getTitle());
        question.setDescription(requestDTO.getDescription());
        question.setCompanyTag(requestDTO.getCompanyTag());
        return question;
    }

    public QuestionResponseDTO toResponseDTO(Question question){
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setDescription(question.getDescription());
        dto.setTopic(question.getTopic());
        dto.setDifficulty(question.getDifficulty());
        dto.setCompanyTag(question.getCompanyTag());
        dto.setStatus(question.getStatus());
        dto.setCreatedAt(question.getCreatedAt());
        return dto;
    }
}
