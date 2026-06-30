package com.example.interview_os.repository;

import com.example.interview_os.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
