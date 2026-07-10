package com.example.interview_os.repository;

import com.example.interview_os.dto.analytics.WeakTopicDTO;
import com.example.interview_os.entity.QuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {
    List<QuestionAttempt> findByQuestionId(Long questionId);
    @Query("SELECT new com.example.interview_os.dto.analytics.WeakTopicDTO(" +
            "q.topic, " +
            "COUNT(qa.id), " +
            "SUM(CASE WHEN qa.status = com.example.interview_os.enums.AttemptStatus.FAILED THEN 1L ELSE 0L END), " +
            "AVG(qa.confidenceScore)) " +
            "FROM QuestionAttempt qa " +
            "JOIN qa.question q " +
            "GROUP BY q.topic " +
            "HAVING COUNT(qa.id) > 0 " +
            "ORDER BY SUM(CASE WHEN qa.status = com.example.interview_os.enums.AttemptStatus.FAILED THEN 1L ELSE 0L END) DESC, " +
            "AVG(qa.confidenceScore) ASC")
    List<WeakTopicDTO> findWeakTopics();
}
