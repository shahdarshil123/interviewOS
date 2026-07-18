package com.example.interview_os.repository;

import com.example.interview_os.entity.MockInterview;
import com.example.interview_os.enums.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MockInterviewRepository
        extends JpaRepository<MockInterview, Long> {

    List<MockInterview> findByUserId(Long userId);

    List<MockInterview> findByUserIdAndStatus(
            Long userId, InterviewStatus status);

    Optional<MockInterview> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT mi FROM MockInterview mi WHERE mi.user.id = :userId " +
            "AND mi.status = com.example.interview_os.enums.InterviewStatus.SCHEDULED " +
            "AND mi.scheduledAt > :now ORDER BY mi.scheduledAt ASC")
    List<MockInterview> findUpcomingInterviews(
            @Param("userId") Long userId,
            @Param("now") LocalDateTime now);
}