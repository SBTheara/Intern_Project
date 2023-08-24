package com.example.mysmallproject.repository.impl;
import com.example.mysmallproject.entity.Users;
import com.example.mysmallproject.repository.UserCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRepositoryImplementation implements UserCustomRepository {
//    @Autowired
//    private EntityManager em;
//    @Override
//    public List<Users> findByFirstnameOrLastnameOrEmail(String firstname, String lastname, String email) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Users> query = cb.createQuery(Users.class);
//        Root<Users> root = query.from(Users.class);
//        Predicate hasFirstname= cb.equal(root.get("firstname"),firstname);
//        Predicate hasLastname = cb.equal(root.get("lastname"),lastname);
//        Predicate hasEmail = cb.equal(root.get("email"),email);
//        query.where(hasFirstname,hasLastname,hasEmail);
//        TypedQuery<Users> qery = em.createQuery(query);
//        return qery.getResultList();
//    }
}
