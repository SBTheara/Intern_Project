package com.intern.project.utils;

import com.intern.project.entity.Questions;
import com.intern.project.entity.Questions_;
import org.springframework.data.jpa.domain.Specification;

public class QuestionSpecification extends SpecificationUtil {
    public static Specification<Questions> filterType(String type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(
                        criteriaBuilder.function(
                                "REPLACE",
                                String.class,
                                root.get(Questions_.type),
                                criteriaBuilder.literal(" "),
                                criteriaBuilder.literal(""))),
                "%" + type.replaceAll(" ", "").toUpperCase() + "%");
    }
    public static Specification<Questions> filterLevel(String level) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(
                        criteriaBuilder.function(
                                "REPLACE",
                                String.class,
                                root.get(Questions_.level),
                                criteriaBuilder.literal(" "),
                                criteriaBuilder.literal(""))),
                "%" + level.replaceAll(" ", "").toUpperCase() + "%");
    }
}
