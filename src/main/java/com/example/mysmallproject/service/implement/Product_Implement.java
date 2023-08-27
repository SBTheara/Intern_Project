package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.Exception.ApiRequestException;
import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.repository.Product_Repository;
import com.example.mysmallproject.service.Products_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class Product_Implement implements Products_Service {
    @Autowired
    private Product_Repository productRepository;
    @Override
    public Products saveProduct(Products products) {
        return productRepository.save(products);
    }
    @Override
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Products getById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new ApiRequestException("Not found for this product"));
    }

    @Override
    public Products updateProduct(Products products, int id) {
        Products pro;
        pro = productRepository.findById(id).orElseThrow(() -> new ApiRequestException("Not found for this product"));
        pro.setId(products.getId());
        pro.setName(products.getName());
        pro.setDescription(products.getDescription());
        pro.setQuantity(products.getQuantity());
        pro.setPrice(products.getPrice());
        pro.setCreateAt(products.getCreateAt());
        pro.setImage(products.getImage());
        productRepository.save(pro);
        return pro;
    }

    @Override
    public Page<Products> getProductsByPaginations(int offset, int pagesize) {
        Pageable pageable = PageRequest.of(offset, pagesize);
        return productRepository.findAll(pageable);
    }
    @Override
    public Page<Products> getProductsByPaginationsAndSort(int offset, int pagesize, String field) {
        return productRepository.findAll(PageRequest.of(offset,pagesize).withSort(Sort.by(field)));
    }
    @Override
    public void deleteProducts(int id) {
        productRepository.deleteById(id);
    }

}
