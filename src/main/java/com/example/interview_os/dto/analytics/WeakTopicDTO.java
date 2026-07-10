package com.example.interview_os.dto.analytics;

import com.example.interview_os.enums.Topic;

public class WeakTopicDTO {
    private Topic topic;
    private Long totalAttempts;
    private Long failedAttempts;
    private Double averageConfidence;
    private Double failureRate;

    public WeakTopicDTO(Topic topic, Long totalAttempts, Long failedAttempts, Double averageConfidence){
        this.topic = topic;
        this.totalAttempts = totalAttempts;
        this.failedAttempts = failedAttempts;
        this.averageConfidence = averageConfidence;
        this.failureRate = totalAttempts > 0 ? (double) failedAttempts / totalAttempts * 100: 0.0;
    }

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

    public Long getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Long failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public Double getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(Double failureRate) {
        this.failureRate = failureRate;
    }

    public Double getAverageConfidence() {
        return averageConfidence;
    }

    public void setAverageConfidence(Double averageConfidence) {
        this.averageConfidence = averageConfidence;
    }
}

