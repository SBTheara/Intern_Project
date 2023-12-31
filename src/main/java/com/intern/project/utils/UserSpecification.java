package com.intern.project.utils;

import com.intern.project.entity.User;
import com.intern.project.entity.User_;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;
public class UserSpecification extends SpecificationUtil {
  public static Specification<User> filterAndSearch(String address, String search) {
    if (StringUtils.isNotBlank(address) || StringUtils.isNotBlank(search)) {
      return (root, query, criteriaBuilder) ->
          criteriaBuilder.and(
              searchName(root, query, criteriaBuilder, User_.ADDRESS, address),
              criteriaBuilder.or(
                  searchID(root, query, criteriaBuilder, User_.ID, search),
                  searchFirstNameAndLastName(
                      root, query, criteriaBuilder, User_.FIRST_NAME, User_.LAST_NAME, search)));
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
  }
  public static Specification<User> withFilterAddress (String address){
    return (root, query, criteriaBuilder) ->
            searchName(root, query, criteriaBuilder, User_.ADDRESS, address);
  }
  public static Specification<User> withFilterSearch(String search){
    return (root,query,criteriaBuilder) -> criteriaBuilder.or(
            searchID(root, query, criteriaBuilder, User_.ID, search),
            searchFirstNameAndLastName(
                    root, query, criteriaBuilder, User_.FIRST_NAME, User_.LAST_NAME, search));
  }
}
