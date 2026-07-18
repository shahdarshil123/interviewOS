package com.example.interview_os.service;

import com.example.interview_os.dto.FeedbackRequestDTO;
import com.example.interview_os.dto.FeedbackResponseDTO;
import com.example.interview_os.dto.MockInterviewRequestDTO;
import com.example.interview_os.dto.MockInterviewResponseDTO;
import com.example.interview_os.entity.InterviewFeedback;
import com.example.interview_os.entity.MockInterview;
import com.example.interview_os.entity.User;
import com.example.interview_os.enums.InterviewStatus;
import com.example.interview_os.exception.ResourceNotFoundException;
import com.example.interview_os.mapper.FeedbackMapper;
import com.example.interview_os.mapper.MockInterviewMapper;
import com.example.interview_os.repository.FeedbackRepository;
import com.example.interview_os.repository.MockInterviewRepository;
import com.example.interview_os.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockInterviewServiceImpl implements MockInterviewService {

    private final MockInterviewRepository interviewRepository;
    private final MockInterviewMapper interviewMapper;
    private final SecurityUtils securityUtils;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public MockInterviewServiceImpl(
            MockInterviewRepository interviewRepository,
            MockInterviewMapper interviewMapper,
            SecurityUtils securityUtils,
            FeedbackRepository feedbackRepository,
            FeedbackMapper feedbackMapper) {
        this.interviewRepository = interviewRepository;
        this.interviewMapper = interviewMapper;
        this.securityUtils = securityUtils;
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    @Transactional
    public FeedbackResponseDTO addFeedback(Long interviewId,
                                           FeedbackRequestDTO requestDTO) {
        User currentUser = securityUtils.getCurrentUser();

        MockInterview interview = interviewRepository
                .findByIdAndUserId(interviewId, currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "MockInterview", interviewId));

        if (feedbackRepository.findByInterviewId(interviewId).isPresent()) {
            throw new RuntimeException(
                    "Feedback already exists for this interview");
        }

        InterviewFeedback feedback = feedbackMapper.toEntity(requestDTO);
        feedback.setInterview(interview);

        interview.setStatus(InterviewStatus.COMPLETED);
        interviewRepository.save(interview);

        InterviewFeedback saved = feedbackRepository.save(feedback);
        return feedbackMapper.toResponseDTO(saved);
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