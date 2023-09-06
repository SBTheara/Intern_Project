package com.intern.project.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SpecificationUtil {
  public static Predicate searchID(
      Root<?> root,
      CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder,
      String field,
      String search) {
    return criteriaBuilder.like(criteriaBuilder.toString(root.get(field)), search);
  }

  public static Predicate searchFirstNameAndLastName(
      Root<?> root,
      CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder,
      String firstName,
      String lastName,
      String search) {
    return criteriaBuilder.like(
        criteriaBuilder.upper(criteriaBuilder.concat(root.get(firstName), root.get(lastName))),
        "%" + search.replaceAll("\\s", "").toUpperCase() + "%");
  }

  public static Predicate searchName(
      Root<?> root,
      CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder,
      String field,
      String search) {
    return criteriaBuilder.like(
        criteriaBuilder.upper(
            criteriaBuilder.function(
                "REPLACE",
                String.class,
                root.get(field),
                criteriaBuilder.literal(" "),
                criteriaBuilder.literal(""))),
        "%" + search.replaceAll(" ", "").toUpperCase() + "%");
  }
}
