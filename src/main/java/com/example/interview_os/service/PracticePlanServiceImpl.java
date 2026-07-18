package com.example.interview_os.service;

import com.example.interview_os.dto.dashboard.PracticePlanDTO;
import com.example.interview_os.dto.dashboard.PracticeRecommendationDTO;
import com.example.interview_os.entity.Question;
import com.example.interview_os.entity.User;
import com.example.interview_os.enums.QuestionStatus;
import com.example.interview_os.repository.QuestionRepository;
import com.example.interview_os.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PracticePlanServiceImpl implements PracticePlanService {

    private final QuestionRepository questionRepository;
    private final SecurityUtils securityUtils;

    public PracticePlanServiceImpl(QuestionRepository questionRepository,
                                   SecurityUtils securityUtils) {
        this.questionRepository = questionRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public PracticePlanDTO getTodaysPracticePlan() {
        User currentUser = securityUtils.getCurrentUser();
        Long userId = currentUser.getId();
        List<PracticeRecommendationDTO> recommendations = new ArrayList<>();

        // Priority 1 — Not attempted questions
        List<Question> notAttempted = questionRepository
                .findByUserIdAndStatus(userId, QuestionStatus.NOT_ATTEMPTED);

        notAttempted.stream().limit(3).forEach(q ->
                recommendations.add(new PracticeRecommendationDTO(
                        q.getTopic(),
                        "Not attempted yet — start here",
                        q.getId(),
                        q.getTitle(),
                        "HIGH"
                ))
        );

        // Priority 2 — Low confidence questions
        List<Question> lowConfidence = questionRepository
                .findByUserIdAndConfidenceScoreLessThanAndStatusNot(
                        userId, 5, QuestionStatus.NOT_ATTEMPTED);

        lowConfidence.stream().limit(3).forEach(q ->
                recommendations.add(new PracticeRecommendationDTO(
                        q.getTopic(),
                        "Low confidence score (" + q.getConfidenceScore()
                                + "/10) — needs more practice",
                        q.getId(),
                        q.getTitle(),
                        "HIGH"
                ))
        );

        // Priority 3 — Overdue questions (not practiced in 7 days)
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
        List<Question> overdue = questionRepository
                .findOverdueQuestions(userId, cutoffDate);

        overdue.stream().limit(3).forEach(q ->
                recommendations.add(new PracticeRecommendationDTO(
                        q.getTopic(),
                        "Not practiced in over 7 days — revision needed",
                        q.getId(),
                        q.getTitle(),
                        "MEDIUM"
                ))
        );

        String summary = buildSummary(notAttempted.size(),
                lowConfidence.size(), overdue.size());

        return new PracticePlanDTO(summary, recommendations);
    }

    private String buildSummary(int notAttempted,
                                int lowConfidence, int overdue) {
        if (notAttempted == 0 && lowConfidence == 0 && overdue == 0) {
            return "Great job! You are on track. Keep practicing daily.";
        }

        StringBuilder summary = new StringBuilder("Today's focus: ");
        if (notAttempted > 0) {
            summary.append(notAttempted)
                    .append(" new questions to attempt. ");
        }
        if (lowConfidence > 0) {
            summary.append(lowConfidence)
                    .append(" questions need confidence building. ");
        }
        if (overdue > 0) {
            summary.append(overdue)
                    .append(" questions overdue for revision.");
        }
        return summary.toString().trim();
    }
}