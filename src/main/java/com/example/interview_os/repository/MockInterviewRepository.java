package com.example.interview_os.repository;

import com.example.interview_os.entity.MockInterview;
import com.example.interview_os.enums.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MockInterviewRepository
        extends JpaRepository<MockInterview, Long> {

    List<MockInterview> findByUserId(Long userId);

    List<MockInterview> findByUserIdAndStatus(
            Long userId, InterviewStatus status);

    Optional<MockInterview> findByIdAndUserId(Long id, Long userId);
}