package com.example.interview_os.repository;

import com.example.interview_os.entity.Story;
import com.example.interview_os.enums.StoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findByUserId(Long userId);

    List<Story> findByUserIdAndCategory(Long userId, StoryCategory category);

    Optional<Story> findByIdAndUserId(Long id, Long userId);
}