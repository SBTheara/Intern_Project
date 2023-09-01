package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProduct();
    Product getById(int id);
    Product updateProduct(Product product, int id);
    Page<Product> getProductsByPagination(int offset, int pagesize);
    Page<Product> getProductsByPaginationsAndSort(int offset, int pagesize, String field);
    void deleteProducts(int id);

}
