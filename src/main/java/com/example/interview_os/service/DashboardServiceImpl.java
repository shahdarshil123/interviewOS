package com.example.interview_os.service;

import com.example.interview_os.dto.dashboard.ReadinessDTO;
import com.example.interview_os.dto.dashboard.TopicBreakdownDTO;
import com.example.interview_os.dto.dashboard.UpcomingInterviewDTO;
import com.example.interview_os.entity.MockInterview;
import com.example.interview_os.entity.User;
import com.example.interview_os.enums.QuestionStatus;
import com.example.interview_os.repository.MockInterviewRepository;
import com.example.interview_os.repository.QuestionAttemptRepository;
import com.example.interview_os.repository.QuestionRepository;
import com.example.interview_os.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final QuestionRepository questionRepository;
    private final QuestionAttemptRepository attemptRepository;
    private final MockInterviewRepository interviewRepository;
    private final SecurityUtils securityUtils;

    public DashboardServiceImpl(QuestionRepository questionRepository,
                                QuestionAttemptRepository attemptRepository,
                                MockInterviewRepository interviewRepository,
                                SecurityUtils securityUtils) {
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
        this.interviewRepository = interviewRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public ReadinessDTO getReadiness() {
        User currentUser = securityUtils.getCurrentUser();
        Long userId = currentUser.getId();

        Long totalQuestions = questionRepository.countByUserId(userId);
        Long solvedQuestions = questionRepository
                .countByUserIdAndStatus(userId, QuestionStatus.SOLVED);
        Long totalAttempts = attemptRepository
                .countByQuestionUserId(userId);
        Double averageConfidence = attemptRepository
                .findAverageConfidenceByUserId(userId);

        if (averageConfidence == null) averageConfidence = 0.0;

        int overallScore = calculateReadinessScore(
                totalQuestions, solvedQuestions,
                totalAttempts, averageConfidence);

        String readinessLevel = getReadinessLevel(overallScore);
        String summary = buildReadinessSummary(
                readinessLevel, solvedQuestions, totalQuestions);

        return new ReadinessDTO(overallScore, readinessLevel,
                totalQuestions, solvedQuestions,
                totalAttempts, averageConfidence, summary);
    }

    @Override
    public List<TopicBreakdownDTO> getTopicBreakdown() {
        User currentUser = securityUtils.getCurrentUser();

        List<Object[]> results = questionRepository
                .findTopicBreakdownByUserId(currentUser.getId());

        return results.stream().map(row ->
                new TopicBreakdownDTO(
                        (com.example.interview_os.enums.Topic) row[0],
                        (Long) row[1],
                        (Long) row[2],
                        (Long) row[3],
                        row[4] != null ? (Double) row[4] : 0.0
                )
        ).collect(Collectors.toList());
    }

    @Override
    public List<UpcomingInterviewDTO> getUpcomingInterviews() {
        User currentUser = securityUtils.getCurrentUser();

        List<MockInterview> upcoming = interviewRepository
                .findUpcomingInterviews(
                        currentUser.getId(), LocalDateTime.now());

        return upcoming.stream().map(interview -> {
            Long daysUntil = ChronoUnit.DAYS.between(
                    LocalDateTime.now(), interview.getScheduledAt());
            return new UpcomingInterviewDTO(
                    interview.getId(),
                    interview.getCompany(),
                    interview.getType(),
                    interview.getScheduledAt(),
                    daysUntil
            );
        }).collect(Collectors.toList());
    }

    private int calculateReadinessScore(Long total, Long solved,
                                        Long attempts, Double avgConfidence) {
        if (total == 0) return 0;

        double solvedPercentage = (double) solved / total * 100;
        double attemptBonus = Math.min(attempts * 2, 20);
        double confidenceScore = avgConfidence * 3;

        return (int) Math.min(
                (solvedPercentage * 0.5) + attemptBonus + confidenceScore,
                100);
    }

    private String getReadinessLevel(int score) {
        if (score >= 80) return "INTERVIEW_READY";
        if (score >= 60) return "ALMOST_READY";
        if (score >= 40) return "IN_PROGRESS";
        return "NEEDS_WORK";
    }

    private String buildReadinessSummary(String level,
                                         Long solved, Long total) {
        return switch (level) {
            case "INTERVIEW_READY" -> "You are interview ready! " +
                    "Solved " + solved + " of " + total + " questions.";
            case "ALMOST_READY" -> "Almost there! Focus on solving " +
                    "more questions and improving confidence.";
            case "IN_PROGRESS" -> "Good progress! Keep practicing " +
                    "daily to build your readiness.";
            default -> "Just getting started. Aim to solve at least " +
                    "50% of your questions.";
        };
    }
}   