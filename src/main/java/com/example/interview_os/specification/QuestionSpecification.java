package com.example.interview_os.specification;

import com.example.interview_os.entity.Question;
import com.example.interview_os.enums.Difficulty;
import com.example.interview_os.enums.Topic;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecification {

    public static Specification<Question> hasTopic(Topic topic) {
        return (root, query, criteriaBuilder) -> {
            if (topic == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("topic"), topic);
        };
    }

    public static Specification<Question> hasDifficulty(Difficulty difficulty) {
        return (root, query, criteriaBuilder) -> {
            if (difficulty == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("difficulty"), difficulty);
        };
    }

    public static Specification<Question> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Question> hasCompanyTag(String companyTag) {
        return (root, query, criteriaBuilder) -> {
            if (companyTag == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.get("companyTag"), companyTag);
        };
    }

}

