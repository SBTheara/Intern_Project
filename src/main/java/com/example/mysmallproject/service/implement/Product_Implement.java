package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.repository.Product_Repository;
import com.example.mysmallproject.service.Products_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class Product_Implement implements Products_Service {
    @Autowired
    private Product_Repository productRepository;
    @Override
    public Products SaveProduct(Products products) {
        return productRepository.save(products);
    }

    @Override
    public List<Products> GetAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Page<Products> GetProductsByPaginations(int offset, int pagesize) {
        Pageable pageable = PageRequest.of(offset, pagesize);
        return productRepository.findAll(pageable);
    }
    @Override
    public Page<Products> GetProductsByPaginationsAndSort(int offset, int pagesize, String field) {
        return productRepository.findAll(PageRequest.of(offset,pagesize).withSort(Sort.by(field)));
    }

    @Override
    public List<Products> GetAllProductsBySorting(String field) {
        return productRepository.findAllBySorting(field);
    }

    @Override
    public byte[] DownloadImage(String filename) throws IOException {
        return new byte[0];
    }
    @Override
    public void DeleteProducts(int id) {
        productRepository.deleteById(id);
    }

//    @Override
//    public List<Products> SearchProductByID(int id) {
//        return productRepository.findAllByProduct_id(id);
//    }
//
//    @Override
//    public List<Products> SearchProductByName(String field) {
//        return productRepository.findAllByName(field);
//    }

    @Override
    public List<Products> SearchProductByNameOrID(String field) {
        String f = "%"+field+"%";
        return productRepository.findAllByNameOrProduct_id(field,f);
    }

}
