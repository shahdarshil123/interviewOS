package com.example.interview_os.service;

import com.example.interview_os.dto.MockInterviewRequestDTO;
import com.example.interview_os.dto.MockInterviewResponseDTO;
import com.example.interview_os.enums.InterviewStatus;
import java.util.List;

public interface MockInterviewService {
    MockInterviewResponseDTO createInterview(
            MockInterviewRequestDTO requestDTO);
    List<MockInterviewResponseDTO> getAllInterviews(
            InterviewStatus status);
}