package com.example.interview_os.service;

import com.example.interview_os.dto.dashboard.ReadinessDTO;
import com.example.interview_os.dto.dashboard.TopicBreakdownDTO;
import com.example.interview_os.dto.dashboard.UpcomingInterviewDTO;
import java.util.List;

public interface DashboardService {
    ReadinessDTO getReadiness();
    List<TopicBreakdownDTO> getTopicBreakdown();
    List<UpcomingInterviewDTO> getUpcomingInterviews();
}