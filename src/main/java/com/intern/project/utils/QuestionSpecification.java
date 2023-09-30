package com.intern.project.utils;

import com.intern.project.entity.Questions;
import com.intern.project.entity.Questions_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class QuestionSpecification extends SpecificationUtil{
    public static Specification<Questions> filter (String type){
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Questions_.type),type);
    }
}
