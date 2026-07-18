package com.example.interview_os.dto.dashboard;

import com.example.interview_os.enums.Topic;

public class PracticeRecommendationDTO {

    private Topic topic;
    private String reason;
    private Long questionId;
    private String questionTitle;
    private String priority;

    public PracticeRecommendationDTO(Topic topic, String reason,
                                     Long questionId, String questionTitle,
                                     String priority) {
        this.topic = topic;
        this.reason = reason;
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.priority = priority;
    }

    // generate getters only

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}