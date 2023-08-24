package com.example.mysmallproject.specifications;

import com.example.mysmallproject.entity.Users;
import com.example.mysmallproject.entity.Users_;
import com.example.mysmallproject.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
@Component
public class UserSpacifications {
    public static Specification<Users> search(String field){
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Users_.FIRSTNAME)),"%"+field.toUpperCase()+"%"),
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Users_.LASTNAME)),"%"+field.toUpperCase()+"%"),
                criteriaBuilder.like(
                        criteriaBuilder.upper(
                        criteriaBuilder.concat(root.get(Users_.FIRSTNAME),root.get(Users_.LASTNAME))),"%"+field.replaceAll("\\s","").toUpperCase()+"%"),
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Users_.EMAIL)),"%"+field.toUpperCase()+"%"),
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(Users_.ADDRESS)),"%"+field+"%")
        );
    };
}
