package com.intern.project.utils;

import com.intern.project.entity.Product;
import com.intern.project.entity.Product_;
import com.intern.project.exception.ProductBadRequesException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class ProductSpecification extends SpecificationUtil<Product> {
  public static Specification<Product> searchNameOrId(String search) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.or(
            searchName(root, query, criteriaBuilder, Product_.NAME, search),
            searchID(root, query, criteriaBuilder, Product_.ID, search));
  }
  public static Specification<Product> search(Specification<Product> specification, String search){
    boolean isSearchExist = StringUtils.hasText(search);
    if (isSearchExist) {
      specification = specification.and(ProductSpecification.searchNameOrId(search));
    }
    return specification;
  }
  public static Specification<Product> withFilterMinPrice(String field, Double minPrice) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(field), minPrice);
  }

  public static Specification<Product> withFilterMaxPrice(String field, Double maxPrice) {
    return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(field), maxPrice);
  }

  public static Specification<Product> withFilterFieldHas(
      Specification<Product> specification,String field ,Double minPrice, Double maxPrice) {
    boolean isMinPriceExist = Objects.requireNonNullElse(minPrice, 0D) > 0D;
    if (isMinPriceExist) {
      specification =
          specification.and(ProductSpecification.withFilterMinPrice(field, minPrice));
    }
    boolean isMaxPriceExist = Objects.requireNonNullElse(maxPrice, 0D) > 0D;
    if (isMaxPriceExist) {
      specification =
          specification.and(ProductSpecification.withFilterMaxPrice(field, maxPrice));
    }
    boolean isInvalidPrice = isMinPriceExist && isMaxPriceExist && minPrice > maxPrice;
    if (isInvalidPrice) {
      throw new ProductBadRequesException("Min " + field + " must be less than max " + field);
    }
    return specification;
  }
}
