package com.example.mysmallproject.specifications;
import com.example.mysmallproject.entity.Users;
import com.example.mysmallproject.entity.Users_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class UserSpacifications {
    public static Specification<Users> search(String field){
        String param = "%"+field.replaceAll("\\s","").toUpperCase()+"%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Users_.FIRSTNAME)),param),
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Users_.LASTNAME)),param),
                criteriaBuilder.like(
                        criteriaBuilder.upper(
                        criteriaBuilder.concat(root.get(Users_.FIRSTNAME),root.get(Users_.LASTNAME))),param),
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Users_.EMAIL)),param),
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Users_.ADDRESS)),param)
        );
    };
    public static Specification<Users> filter(String field, String search){
        if(field!=null && search!=null){
            return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.like(
                            criteriaBuilder.upper(
                                    criteriaBuilder.concat(root.get(Users_.FIRSTNAME),root.get(Users_.LASTNAME))),"%"+search.replaceAll("\\s","").toUpperCase()+"%"),
                    criteriaBuilder.like(root.get(Users_.ADDRESS),field)
            );
        }else {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

    }
}
