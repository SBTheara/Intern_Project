package com.intern.project.utils;

import com.intern.project.entity.Product;
import com.intern.project.entity.Product_;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification extends SpecificationUtil<Product> {
  public static Specification<Product> searchNameOrId(String search) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.or(
            searchName(root, query, criteriaBuilder, Product_.NAME, search),
            searchID(root, query, criteriaBuilder, Product_.ID, search));
  }

  public static Specification<Product> withFilterMinPrice(Double minPrice) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), minPrice);
  }

  public static Specification<Product> withFilterMaxPrice(Double maxPrice) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), maxPrice);
  }
}
