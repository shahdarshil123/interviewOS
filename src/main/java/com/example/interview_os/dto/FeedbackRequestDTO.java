package com.example.interview_os.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class FeedbackRequestDTO {

    private String strengths;
    private String weaknesses;

    @Min(value = 1, message = "Technical score must be at least 1")
    @Max(value = 10, message = "Technical score must be at most 10")
    private Integer technicalScore;

    @Min(value = 1, message = "Communication score must be at least 1")
    @Max(value = 10, message = "Communication score must be at most 10")
    private Integer communicationScore;

    private String nextSteps;

    // generate getters and setters

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
    }

    public Integer getTechnicalScore() {
        return technicalScore;
    }

    public void setTechnicalScore(Integer technicalScore) {
        this.technicalScore = technicalScore;
    }

    public Integer getCommunicationScore() {
        return communicationScore;
    }

    public void setCommunicationScore(Integer communicationScore) {
        this.communicationScore = communicationScore;
    }

    public String getNextSteps() {
        return nextSteps;
    }

    public void setNextSteps(String nextSteps) {
        this.nextSteps = nextSteps;
    }
}