package com.example.interview_os.repository;

import com.example.interview_os.entity.InterviewFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FeedbackRepository
        extends JpaRepository<InterviewFeedback, Long> {

    Optional<InterviewFeedback> findByInterviewId(Long interviewId);
}