package com.example.mysmallproject.specifications;
import com.example.mysmallproject.entity.Users;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class UserSpacifications {
    public static Specification<Users> search(String field){
        String param = "%"+field.replaceAll("\\s","").toUpperCase()+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        criteriaBuilder.upper(
                        criteriaBuilder.concat(root.get("firstname"),root.get("lastname"))),param),
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("email")),param),
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("address")),param)
        );
    };
    public static Specification<Users> filter(String field, String search){
        if(field!=null && search!=null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    criteriaBuilder.concat(root.get("firstname"),root.get("lastname"))),"%"+search.replaceAll("\\s","").toUpperCase()+"%"),
                    criteriaBuilder.like(root.get("address"),field)
            );
        }else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

    }
}
