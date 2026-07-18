package com.example.interview_os.dto.dashboard;

import com.example.interview_os.enums.Topic;

public class TopicBreakdownDTO {

    private Topic topic;
    private Long totalQuestions;
    private Long solvedQuestions;
    private Long attemptedQuestions;
    private Double averageConfidence;
    private String mastery;

    public TopicBreakdownDTO(Topic topic, Long totalQuestions,
                             Long solvedQuestions, Long attemptedQuestions,
                             Double averageConfidence) {
        this.topic = topic;
        this.totalQuestions = totalQuestions;
        this.solvedQuestions = solvedQuestions;
        this.attemptedQuestions = attemptedQuestions;
        this.averageConfidence = averageConfidence;
        this.mastery = calculateMastery(solvedQuestions, totalQuestions);
    }

    private String calculateMastery(Long solved, Long total) {
        if (total == 0) return "NOT_STARTED";
        double percentage = (double) solved / total * 100;
        if (percentage >= 80) return "STRONG";
        if (percentage >= 50) return "DEVELOPING";
        if (percentage > 0) return "WEAK";
        return "NOT_STARTED";
    }

    // generate getters only


    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Long totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Long getSolvedQuestions() {
        return solvedQuestions;
    }

    public void setSolvedQuestions(Long solvedQuestions) {
        this.solvedQuestions = solvedQuestions;
    }

    public Long getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(Long attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }

    public Double getAverageConfidence() {
        return averageConfidence;
    }

    public void setAverageConfidence(Double averageConfidence) {
        this.averageConfidence = averageConfidence;
    }

    public String getMastery() {
        return mastery;
    }

    public void setMastery(String mastery) {
        this.mastery = mastery;
    }
}