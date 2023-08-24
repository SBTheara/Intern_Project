package com.example.mysmallproject.repository;

import com.example.mysmallproject.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Product_Repository extends JpaRepository<Products,Integer>, JpaSpecificationExecutor<Products> {
    @Query(value = "SELECT * FROM products order by :field desc;",nativeQuery = true)
    List<Products> findAllBySorting(String field);
    @Query(value = "SELECT * FROM pos.products where product_id = :id",nativeQuery = true)
    List<Products> findAllByProduct_id(int id);
    List<Products> findAllByName(String field);
    //@Query(value = "SELECT * FROM pos.products where product_id = :field OR name like :field",nativeQuery = true)
    @Query(value = "SELECT * FROM pos.products where REPLACE(name, ' ', '') = REPLACE(:field, ' ', '') or \n"+
            "name like :f or product_id = :field OR REPLACE(product_id, ' ', '') = REPLACE(:field, ' ', '')"
            ,nativeQuery = true)
    List<Products> findAllByNameOrProduct_id(String field,String f);
}
