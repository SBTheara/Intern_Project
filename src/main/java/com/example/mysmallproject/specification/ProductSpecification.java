package com.example.mysmallproject.specification;
import com.example.mysmallproject.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class ProductSpecification {
    public static Specification<Product> search (String field){
        String param = "%"+field.replaceAll("\\s","").toUpperCase()+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("name")),param),
                    criteriaBuilder.like(criteriaBuilder.toString(root.get("price")),field)
        );
    }
    public static Specification<Product> filterMaxAndMin(String minPrice, String maxPrice , String search){
        if(minPrice!=null && maxPrice!=null &&search!=null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.and(
                            criteriaBuilder.between(root.get("price"),minPrice,maxPrice),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("name")),"%"+search.replaceAll("\\s","").toUpperCase()+"%"))
            );
        }else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
    }
    public static Specification<Product> filterMin(String minPrice, String search){
        if (minPrice!=null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.and(
                            criteriaBuilder.ge(root.get("price"),Double.parseDouble(minPrice)),
                            criteriaBuilder.like(criteriaBuilder.upper(root.get("name")),"%"+search.replaceAll("\\s","").toUpperCase()+"%"))
            );
        }else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
    }
}
