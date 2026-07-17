package com.example.interview_os.repository;

import com.example.interview_os.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>,
        JpaSpecificationExecutor<Question> {
    List<Question> findByUserId(Long userId);
    Optional<Question> findByIdAndUserId(Long id, Long userId);
}

