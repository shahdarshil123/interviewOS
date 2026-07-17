package com.example.interview_os.mapper;

import com.example.interview_os.dto.MockInterviewRequestDTO;
import com.example.interview_os.dto.MockInterviewResponseDTO;
import com.example.interview_os.entity.MockInterview;
import org.springframework.stereotype.Component;

@Component
public class MockInterviewMapper {

    public MockInterview toEntity(MockInterviewRequestDTO requestDTO) {
        MockInterview interview = new MockInterview();
        interview.setCompany(requestDTO.getCompany());
        interview.setType(requestDTO.getType());
        interview.setScheduledAt(requestDTO.getScheduledAt());
        interview.setNotes(requestDTO.getNotes());
        return interview;
    }

    public MockInterviewResponseDTO toResponseDTO(MockInterview interview) {
        MockInterviewResponseDTO dto = new MockInterviewResponseDTO();
        dto.setId(interview.getId());
        dto.setUserId(interview.getUser().getId());
        dto.setCompany(interview.getCompany());
        dto.setType(interview.getType());
        dto.setStatus(interview.getStatus());
        dto.setScheduledAt(interview.getScheduledAt());
        dto.setNotes(interview.getNotes());
        dto.setCreatedAt(interview.getCreatedAt());
        return dto;
    }
}