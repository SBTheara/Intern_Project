package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.repository.FileDataRepository;
import com.example.mysmallproject.repository.Product_Repository;
import com.example.mysmallproject.service.Products_Service;
import com.example.mysmallproject.utils.image_util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        return productRepository.findAll(PageRequest.of(offset,pagesize));
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

}
