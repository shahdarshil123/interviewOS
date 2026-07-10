package com.example.interview_os.service;

import com.example.interview_os.dto.analytics.WeakTopicDTO;

import java.util.List;

public interface AnalyticsService {
    List<WeakTopicDTO> getWeakTopics();
}


