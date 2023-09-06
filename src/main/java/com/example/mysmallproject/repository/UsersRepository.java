package com.example.mysmallproject.repository;

import com.example.mysmallproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Repository
public interface UsersRepository extends JpaRepository<User,Long> ,JpaSpecificationExecutor<User> {
}
