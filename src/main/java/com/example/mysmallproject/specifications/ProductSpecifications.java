package com.example.mysmallproject.specifications;

import com.example.mysmallproject.entity.Products;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecifications {
    public static Specification<Products> search(String field){
        String param = "%"+field.replaceAll("\\s","").toUpperCase()+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("name")),param),
                    criteriaBuilder.like(criteriaBuilder.toString(root.get("price")),field)
        );
    }
    public static Specification<Products> filter(String field){
        switch(field) {
            case "LOW" -> {
                return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get("price"), 10000);
            }
            case "MEDIUM" -> {
                return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), 10000, 30000);
            }
            case "HIGH" -> {
                return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get("price"), 30000);
            }
            default -> {
                return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
            }
        }
    }
}
