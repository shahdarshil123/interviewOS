package com.example.interview_os.repository;

import com.example.interview_os.entity.QuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {
    List<QuestionAttempt> findByQuestionId(Long questionId);
}
