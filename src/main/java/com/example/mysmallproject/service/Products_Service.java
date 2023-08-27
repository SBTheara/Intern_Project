package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.Products;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface Products_Service {
    Products saveProduct(Products products);
    List<Products> GetAllProducts();
    Products getById(int id);
    Products updateProduct(Products products,int id);
    Page<Products> GetProductsByPaginations(int offset, int pagesize);
    Page<Products> GetProductsByPaginationsAndSort(int offset, int pagesize,String field);
    void DeleteProducts(int id);

}
