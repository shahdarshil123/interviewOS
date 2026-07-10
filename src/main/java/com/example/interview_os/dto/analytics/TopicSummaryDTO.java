package com.example.interview_os.dto.analytics;

import com.example.interview_os.enums.Topic;

public class TopicSummaryDTO {

    private Topic topic;
    private Long totalAttempts;
    private Long solvedCount;
    private Long failedCount;
    private Long partialCount;
    private Double averageConfidence;

    public TopicSummaryDTO(Topic topic, Long totalAttempts,
                           Long solvedCount, Long failedCount,
                           Long partialCount, Double averageConfidence) {
        this.topic = topic;
        this.totalAttempts = totalAttempts;
        this.solvedCount = solvedCount;
        this.failedCount = failedCount;
        this.partialCount = partialCount;
        this.averageConfidence = averageConfidence;
    }

    // generate getters only via IntelliJ — read-only data


    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(Long totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public Long getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(Long solvedCount) {
        this.solvedCount = solvedCount;
    }

    public Long getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(Long failedCount) {
        this.failedCount = failedCount;
    }

    public Long getPartialCount() {
        return partialCount;
    }

    public void setPartialCount(Long partialCount) {
        this.partialCount = partialCount;
    }

    public Double getAverageConfidence() {
        return averageConfidence;
    }

    public void setAverageConfidence(Double averageConfidence) {
        this.averageConfidence = averageConfidence;
    }
}