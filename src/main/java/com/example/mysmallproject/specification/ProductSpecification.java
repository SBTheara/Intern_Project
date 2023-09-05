package com.example.mysmallproject.specification;
import com.example.mysmallproject.entity.Product;
import com.example.mysmallproject.entity.Product_;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class ProductSpecification {
    public static Specification<Product> filterMaxAndMin(
            double minPrice, double maxPrice, String search) {
        if (minPrice != 0 && maxPrice != 0 && StringUtils.isNotBlank(search)) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.or(
                                    criteriaBuilder.like(
                                            criteriaBuilder.upper(
                                                    criteriaBuilder.function(
                                                            "REPLACE",
                                                            String.class,
                                                            root.get(Product_.NAME),
                                                            criteriaBuilder.literal(" "),
                                                            criteriaBuilder.literal(""))),
                                            "%" + search.replaceAll(" ", "").toUpperCase() + "%"),
                                    criteriaBuilder.like(criteriaBuilder.toString(root.get(Product_.ID)), search)),
                            criteriaBuilder.between(root.get(Product_.PRICE), minPrice, maxPrice));
        } else if (minPrice != 0) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.ge(root.get(Product_.PRICE), minPrice),
                            criteriaBuilder.or(
                                    criteriaBuilder.like(
                                            criteriaBuilder.upper(root.get(Product_.NAME)),
                                            "%" + search.replaceAll("\\s", "").toUpperCase() + "%"),
                                    criteriaBuilder.like(criteriaBuilder.toString(root.get(Product_.ID)), search)));
        } else if (StringUtils.isNotBlank(search)) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    criteriaBuilder.function(
                                            "REPLACE",
                                            String.class,
                                            root.get(Product_.NAME),
                                            criteriaBuilder.literal(" "),
                                            criteriaBuilder.literal(""))),
                            "%" + search.replaceAll(" ", "").toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.toString(root.get(Product_.ID)), search));
        } else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

    }
}
