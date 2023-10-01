package com.intern.project.utils;

import com.intern.project.dto.ScoreRange;
import com.intern.project.entity.Questions;
import com.intern.project.entity.Questions_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.domain.Specification;

@ComponentScan
public class QuestionSpecification extends SpecificationUtil<Questions> {
  public static Specification<Questions> filterType(String type) {
    return (root, query, criteriaBuilder) ->
        filterField(root, criteriaBuilder, Questions_.TYPE, type);
  }

  public static Specification<Questions> filterLevel(String level) {
    return (root, query, criteriaBuilder) ->
        filterField(root, criteriaBuilder, Questions_.LEVEL, level);
  }

  public static Specification<Questions> filterScore(String score) {
    return (root, query, criteriaBuilder) ->
        filterScore(root, criteriaBuilder, Questions_.SCORE, score);
  }

  public static Predicate filterField(
      Root<?> root, CriteriaBuilder criteriaBuilder, String entityField, String filterField) {
    return criteriaBuilder.like(
        criteriaBuilder.upper(root.get(entityField)),
        "%" + filterField.replaceAll(" ", "").toUpperCase() + "%");
  }
  public static Predicate filterScore(
      Root<?> root, CriteriaBuilder criteriaBuilder, String entityField, String scoreRange) {
    ScoreRange filterField = ScoreRangeUtil.scoreRange(scoreRange);
    return criteriaBuilder.between(
        root.get(entityField), filterField.getMinScore(), filterField.getMaxScore());
  }
}
