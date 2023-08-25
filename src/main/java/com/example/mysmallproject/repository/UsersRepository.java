package com.example.mysmallproject.repository;

import com.example.mysmallproject.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface UsersRepository extends JpaRepository<Users,Integer> ,JpaSpecificationExecutor<Users> {
    Page<Users> findAll(Specification<Users> users, Pageable pageable);
}
