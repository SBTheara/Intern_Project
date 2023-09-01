package com.example.mysmallproject.specification;
import com.example.mysmallproject.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class UserSpecification {
    public static Specification<User> filterAndSearch(String address, String type, String search){
        if(address!=null && type!=null && search!=null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    criteriaBuilder.concat(root.get("firstname"),root.get("lastname"))),"%"+search.replaceAll("\\s","").toUpperCase()+"%"),
                    criteriaBuilder.like(
                            criteriaBuilder.upper(root.get("address")),"%"+address.replaceAll("\\s","").toUpperCase()+"%"),
                    criteriaBuilder.like(
                            criteriaBuilder.upper(root.get("type")),"%"+type.replaceAll("\\s","").toUpperCase()+"%")
            );
        }else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
    }
}
