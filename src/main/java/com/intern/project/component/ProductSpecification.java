package com.intern.project.component;

import com.intern.project.entity.Product;
import com.intern.project.entity.Product_;
import com.intern.project.utils.SpecificationUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecification extends SpecificationUtil {
  public static Specification<Product> filterMaxAndMin(
      double minPrice, double maxPrice, String search) {
    if (minPrice !=0 && maxPrice!=0 && StringUtils.isNotBlank(search)) {
      return (root, query, criteriaBuilder) ->
          criteriaBuilder.and(
              criteriaBuilder.or(
                  searchName(root, query, criteriaBuilder, Product_.NAME, search),
                  searchID(root, query, criteriaBuilder, Product_.ID, search)),
              criteriaBuilder.between(root.get(Product_.PRICE), minPrice, maxPrice));
    }
    if (minPrice != 0) {
      return (root, query, criteriaBuilder) ->
          criteriaBuilder.and(
              criteriaBuilder.ge(root.get(Product_.PRICE), minPrice),
              criteriaBuilder.or(
                  searchName(root, query, criteriaBuilder, Product_.NAME, search),
                  searchID(root, query, criteriaBuilder, Product_.ID, search)));
    }
    if (StringUtils.isNotBlank(search) && maxPrice == 0 && minPrice == 0) {
      return (root, query, criteriaBuilder) ->
          criteriaBuilder.or(
              searchName(root, query, criteriaBuilder, Product_.NAME, search),
              searchID(root, query, criteriaBuilder, Product_.ID, search));
    }
    return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
  }
}
