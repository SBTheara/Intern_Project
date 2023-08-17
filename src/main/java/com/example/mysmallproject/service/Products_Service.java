package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface Products_Service {
    Products SaveProduct(Products products);
    List<Products> GetAllProducts();
    Page<Products> GetProductsByPaginations(int offset, int pagesize);
    Page<Products> GetProductsByPaginationsAndSort(int offset, int pagesize,String field);
    List<Products> GetAllProductsBySorting(String field);
    byte[] DownloadImage(String filename) throws IOException;
    void DeleteProducts(int id);

}
