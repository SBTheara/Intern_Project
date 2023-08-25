package com.example.mysmallproject.repository;

import com.example.mysmallproject.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Product_Repository extends JpaRepository<Products,Integer>, JpaSpecificationExecutor<Products> {
    Page<Products> findAll(Specification<Products> specification, Pageable pageable);
    Page<Products> findAll(Pageable pageable);
}
