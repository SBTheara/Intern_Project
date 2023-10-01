package com.intern.project.utils;

import com.intern.project.dto.ScoreRange;
import com.intern.project.entity.Questions;
import com.intern.project.entity.Questions_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

@ComponentScan
public class QuestionSpecification extends SpecificationUtil<Questions> {
    private static final List<ScoreRange> scoreRangeList = List.of(ScoreRange.builder().scoreRange("10-50").minScore(10).maxScore(50).build());
    public static Specification<Questions> filterType(String type) {
        return (root, query, criteriaBuilder) -> filterField(root, criteriaBuilder, Questions_.TYPE, type);
    }

    public static Specification<Questions> filterLevel(String level) {
        return (root, query, criteriaBuilder) -> filterField(root, criteriaBuilder, Questions_.LEVEL, level);
    }

    public static Specification<Questions> filterScore(String score) {
        return (root, query, criteriaBuilder) -> filterScoreTest(root, criteriaBuilder, Questions_.SCORE, score);
    }

    public static Predicate filterField(Root<?> root, CriteriaBuilder criteriaBuilder, String entityField, String filterField) {
        return criteriaBuilder.like(
                criteriaBuilder.upper(root.get(entityField)),
                "%" + filterField.replaceAll(" ", "").toUpperCase() + "%");
    }

    public static Predicate filterScore(Root<?> root, CriteriaBuilder criteriaBuilder, String entityField, String filterField) {
        return criteriaBuilder.like(criteriaBuilder.toString(root.get((entityField))), "%" + filterField.replaceAll(" ", "").toUpperCase() + "%");
    }
    public static Predicate filterScoreTest(Root<?> root, CriteriaBuilder criteriaBuilder, String entityField, String scoreRange){
        ScoreRange filterField = new ScoreRange();
        scoreRangeList.forEach(score -> {
            boolean isEqualScoreRange=Objects.equals(filterField.getScoreRange(), scoreRange);
            if(isEqualScoreRange) {
                filterField.setMinScore(score.getMinScore());
                filterField.setMaxScore(score.getMaxScore());
            }
        });
        int hel = filterField.getMinScore();
        return criteriaBuilder.between(root.get(entityField),filterField.getMinScore(),filterField.getMaxScore());
    }
}
