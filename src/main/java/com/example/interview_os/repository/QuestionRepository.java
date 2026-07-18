package com.example.interview_os.repository;

import com.example.interview_os.entity.Question;
import com.example.interview_os.enums.QuestionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>,
        JpaSpecificationExecutor<Question> {
    List<Question> findByUserId(Long userId);
    Optional<Question> findByIdAndUserId(Long id, Long userId);

    // Find questions with low confidence belonging to current user
    List<Question> findByUserIdAndConfidenceScoreLessThanAndStatusNot(
            Long userId, Integer confidenceScore, QuestionStatus status);

    // Find questions not attempted yet
    List<Question> findByUserIdAndStatus(Long userId, QuestionStatus status);

    // Find questions not attempted recently
    @Query("SELECT q FROM Question q WHERE q.user.id = :userId " +
            "AND q.lastAttemptedAt < :cutoffDate " +
            "AND q.status != 'NOT_ATTEMPTED'")
    List<Question> findOverdueQuestions(
            @Param("userId") Long userId,
            @Param("cutoffDate") LocalDateTime cutoffDate);
}

