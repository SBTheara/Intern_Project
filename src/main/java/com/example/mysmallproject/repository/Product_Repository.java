package com.example.mysmallproject.repository;

import com.example.mysmallproject.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Product_Repository extends JpaRepository<Products,Integer> {
    Optional<Products> findByImageName(String filename);
    @Query(value = "SELECT product_id,name,description,quantity,price,image_name,image_file_path,create_at FROM products;",nativeQuery = true)
    public List<Products> findAllProduct();
}
