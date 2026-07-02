package com.example.interview_os.specification;

import com.example.interview_os.entity.Question;
import com.example.interview_os.enums.Difficulty;
import com.example.interview_os.enums.Topic;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecificiation {

    public static Specification<Question> hasTopicSpec(Topic topic) {
        return (root, query, criteriaBuilder) -> {
            if (topic == null) return null;
            return criteriaBuilder.equal(root.get("topic"), topic);
        };
    }

    public static Specification<Question> hasDifficultySpec(Difficulty difficulty) {
        return (root, query, criteriaBuilder) -> {
            if (difficulty == null) return null;
            return criteriaBuilder.equal(root.get("difficulty"), difficulty);
        };
    }

    public static Specification<Question> hasStatusSpec(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
}

