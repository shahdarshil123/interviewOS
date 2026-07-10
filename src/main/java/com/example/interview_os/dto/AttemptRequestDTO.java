package com.example.interview_os.dto;

import com.example.interview_os.enums.AttemptStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AttemptRequestDTO {
    @NotNull(message="Status is required")
    private AttemptStatus status;

    private Integer timeTaken;

    private String approach;

    private String mistakes;

    private String notes;

    @NotNull(message = "Confidence score is required")
    @Min(value=1, message="Confidence score must be at least 1")
    @Max(value=10, message="Confidence score must be at most 10")
    private Integer confidenceScore;

    public String getApproach() {
        return approach;
    }

    public void setApproach(String approach) {
        this.approach = approach;
    }

    public AttemptStatus getStatus() {
        return status;
    }

    public void setStatus(AttemptStatus status) {
        this.status = status;
    }

    public Integer getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Integer timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getMistakes() {
        return mistakes;
    }

    public void setMistakes(String mistakes) {
        this.mistakes = mistakes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(Integer confidenceScore) {
        this.confidenceScore = confidenceScore;
    }
}
