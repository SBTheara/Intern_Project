package com.example.mysmallproject.specifications;
import com.example.mysmallproject.entity.Users;
import com.example.mysmallproject.entity.Users_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class UserSpacifications {
    public static Specification<Users> getinfo(String field){
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(root.get(Users_.firstname),"%"+field+"%"),
                        criteriaBuilder.like(root.get(Users_.lastname),"%"+field+"%"),
                            criteriaBuilder.like(criteriaBuilder.concat(root.get("firstname"),root.get("lastname")),"%"+field.toUpperCase(Locale.ROOT)+"%"),
                                criteriaBuilder.like(root.get(Users_.email),"%"+field+"%"),
                                    criteriaBuilder.like(root.get(Users_.address),"%"+field+"%")
        );
    }
}
