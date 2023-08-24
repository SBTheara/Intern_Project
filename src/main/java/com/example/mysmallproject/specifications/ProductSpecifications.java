package com.example.mysmallproject.specifications;

import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.entity.Products_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecifications {
    public static Specification<Products> getallbyfield(String field){
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(root.get(Products_.NAME),"%"+field+"%"),
                criteriaBuilder.like(criteriaBuilder.toString(root.get(Products_.PRICE)),field)
        );
    }
}
