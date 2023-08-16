package com.example.mysmallproject.service;

import com.example.mysmallproject.entity.Products;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface Products_Service {
    public Products SaveProduct(MultipartFile file, String name, String description, int quantity, double price, Date Create_at) throws IOException;
    public List<Products> GetAllProducts();
    public byte[] DownloadImage(String filename) throws IOException;
    public Products UpdateProducts(int id, MultipartFile file, String name, String description, int quantity, double price, Date Create_at) throws IOException;
    public void DeleteProducts(int id);

}
