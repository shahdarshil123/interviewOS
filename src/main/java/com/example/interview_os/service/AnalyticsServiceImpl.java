package com.example.interview_os.service;

import com.example.interview_os.dto.analytics.TopicSummaryDTO;
import com.example.interview_os.dto.analytics.WeakTopicDTO;
import com.example.interview_os.repository.QuestionAttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    private final QuestionAttemptRepository attemptRepository;

    public AnalyticsServiceImpl(QuestionAttemptRepository attemptRepository){
        this.attemptRepository = attemptRepository;
    }

    @Override
    public List<WeakTopicDTO> getWeakTopics(){
        return attemptRepository.findWeakTopics();
    }

    @Override
    public List<TopicSummaryDTO> getTopicSummary() {
        return attemptRepository.findTopicSummary();
    }


}
