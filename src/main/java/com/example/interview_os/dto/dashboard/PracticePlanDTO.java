package com.example.interview_os.dto.dashboard;

import com.example.interview_os.dto.QuestionResponseDTO;
import java.util.List;

public class PracticePlanDTO {

    private String summary;
    private List<PracticeRecommendationDTO> recommendations;

    public PracticePlanDTO(String summary,
                           List<PracticeRecommendationDTO> recommendations) {
        this.summary = summary;
        this.recommendations = recommendations;
    }

    // generate getters only

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<PracticeRecommendationDTO> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<PracticeRecommendationDTO> recommendations) {
        this.recommendations = recommendations;
    }
}