package com.example.mysmallproject.specification;
import com.example.mysmallproject.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class UserSpecification {
    public static Specification<User> filterAndSearch(String address,String search){
        if(address!=null && search!=null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.or(
                            criteriaBuilder.like(
                                    criteriaBuilder.upper(
                                            criteriaBuilder.concat(root.get("firstName"),root.get("lastName"))),"%"+search.replaceAll("\\s","").toUpperCase()+"%"),
                            criteriaBuilder.like(criteriaBuilder.toString(root.get("id")),search)
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.upper(root.get("address")),"%"+address.replaceAll("\\s","").toUpperCase()+"%")
            );
        }else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
    }
}
