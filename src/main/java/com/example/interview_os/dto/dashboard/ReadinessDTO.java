package com.example.interview_os.dto.dashboard;

public class ReadinessDTO {

    private Integer overallScore;
    private String readinessLevel;
    private Long totalQuestions;
    private Long solvedQuestions;
    private Long totalAttempts;
    private Double averageConfidence;
    private String summary;

    public ReadinessDTO(Integer overallScore, String readinessLevel,
                        Long totalQuestions, Long solvedQuestions,
                        Long totalAttempts, Double averageConfidence,
                        String summary) {
        this.overallScore = overallScore;
        this.readinessLevel = readinessLevel;
        this.totalQuestions = totalQuestions;
        this.solvedQuestions = solvedQuestions;
        this.totalAttempts = totalAttempts;
        this.averageConfidence = averageConfidence;
        this.summary = summary;
    }

    // generate getters only

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public String getReadinessLevel() {
        return readinessLevel;
    }

    public void setReadinessLevel(String readinessLevel) {
        this.readinessLevel = readinessLevel;
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

    public Long getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(Long totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public Double getAverageConfidence() {
        return averageConfidence;
    }

    public void setAverageConfidence(Double averageConfidence) {
        this.averageConfidence = averageConfidence;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}