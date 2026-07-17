package com.example.interview_os.service;

import com.example.interview_os.dto.MockInterviewRequestDTO;
import com.example.interview_os.dto.MockInterviewResponseDTO;
import com.example.interview_os.entity.MockInterview;
import com.example.interview_os.entity.User;
import com.example.interview_os.enums.InterviewStatus;
import com.example.interview_os.mapper.MockInterviewMapper;
import com.example.interview_os.repository.MockInterviewRepository;
import com.example.interview_os.security.SecurityUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockInterviewServiceImpl implements MockInterviewService {

    private final MockInterviewRepository interviewRepository;
    private final MockInterviewMapper interviewMapper;
    private final SecurityUtils securityUtils;

    public MockInterviewServiceImpl(
            MockInterviewRepository interviewRepository,
            MockInterviewMapper interviewMapper,
            SecurityUtils securityUtils) {
        this.interviewRepository = interviewRepository;
        this.interviewMapper = interviewMapper;
        this.securityUtils = securityUtils;
    }

    @Override
    public MockInterviewResponseDTO createInterview(
            MockInterviewRequestDTO requestDTO) {
        User currentUser = securityUtils.getCurrentUser();
        MockInterview interview = interviewMapper.toEntity(requestDTO);
        interview.setUser(currentUser);
        MockInterview saved = interviewRepository.save(interview);
        return interviewMapper.toResponseDTO(saved);
    }

    @Override
    public List<MockInterviewResponseDTO> getAllInterviews(
            InterviewStatus status) {
        User currentUser = securityUtils.getCurrentUser();

        List<MockInterview> interviews = status != null
                ? interviewRepository.findByUserIdAndStatus(
                currentUser.getId(), status)
                : interviewRepository.findByUserId(currentUser.getId());

        return interviews.stream()
                .map(interviewMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}