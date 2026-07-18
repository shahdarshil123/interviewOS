package com.example.interview_os.dto.dashboard;

import com.example.interview_os.enums.InterviewType;
import java.time.LocalDateTime;

public class UpcomingInterviewDTO {

    private Long id;
    private String company;
    private InterviewType type;
    private LocalDateTime scheduledAt;
    private Long daysUntilInterview;

    public UpcomingInterviewDTO(Long id, String company,
                                InterviewType type,
                                LocalDateTime scheduledAt,
                                Long daysUntilInterview) {
        this.id = id;
        this.company = company;
        this.type = type;
        this.scheduledAt = scheduledAt;
        this.daysUntilInterview = daysUntilInterview;
    }

    // generate getters only

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDaysUntilInterview() {
        return daysUntilInterview;
    }

    public void setDaysUntilInterview(Long daysUntilInterview) {
        this.daysUntilInterview = daysUntilInterview;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public InterviewType getType() {
        return type;
    }

    public void setType(InterviewType type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}