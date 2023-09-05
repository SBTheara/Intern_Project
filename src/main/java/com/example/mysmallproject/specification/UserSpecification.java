package com.example.mysmallproject.specification;

import com.example.mysmallproject.entity.User;
import com.example.mysmallproject.entity.User_;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification {
  public static Specification<User> filterAndSearch(String address, String search) {
    if (StringUtils.isNotBlank(address) || StringUtils.isNotBlank(search)) {
      return (root, query, criteriaBuilder) ->
          criteriaBuilder.and(
              criteriaBuilder.like(
                  criteriaBuilder.upper(
                      criteriaBuilder.function(
                          "REPLACE",
                          String.class,
                          root.get(User_.ADDRESS),
                          criteriaBuilder.literal(" "),
                          criteriaBuilder.literal(""))),
                        "%" + address.replaceAll(" ", "").toUpperCase() + "%"),
              criteriaBuilder.or(
                  criteriaBuilder.like(criteriaBuilder.toString(root.get(User_.ID)), search),
                  criteriaBuilder.like(
                      criteriaBuilder.upper(
                          criteriaBuilder.concat(
                              root.get(User_.FIRST_NAME), root.get(User_.LAST_NAME))),
                      "%" + search.replaceAll("\\s", "").toUpperCase() + "%")));
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
  }
}
