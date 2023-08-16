package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.repository.Product_Repository;
import com.example.mysmallproject.service.Products_Service;
import com.example.mysmallproject.utils.image_util;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final String FILE_PATH ="C:\\Users\\Public\\Pictures\\";
    @Override
    public Products SaveProduct(MultipartFile file, String name, String description, int quantity, double price,Date Create_at) throws IOException {

        String randomname = UUID.randomUUID().toString();
        String filepath =FILE_PATH + file.getOriginalFilename();
        Products productsdata = productRepository.save(Products.builder()
                .name(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .Create_at(Create_at)
                .image(image_util.compressImage(file.getBytes()))
                .imageFilePath(filepath)
                .imageName(randomname)
                .build()
        );
        file.transferTo(new File(filepath));
        if(productsdata != null){
            return productsdata;
        }
        return null;
    }
    @Override
    public List<Products> GetAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public byte[] DownloadImage(String filename) throws IOException {
        Products dbImageData= productRepository.findByImageName(filename).get();
        String filepath=dbImageData.getImageFilePath();
        byte[] image = Files.readAllBytes(new File(filepath).toPath());
        return image;
    }
    @Override
    public Products UpdateProducts(int id, MultipartFile file, String name, String description, int quantity, double price, Date Create_at) throws IOException {
        String randomname = UUID.randomUUID().toString();
        String filepath =FILE_PATH + file.getOriginalFilename();
        Products pro = productRepository.findById(id).get();
        pro.setName(name);
        pro.setDescription(description);
        pro.setQuantity(quantity);
        pro.setPrice(price);
        pro.setCreate_at(Create_at);
        pro.setImage(image_util.compressImage(file.getBytes()));
        pro.setImageFilePath(filepath);
        pro.setImageName(randomname);
        file.transferTo(new File(filepath));
        productRepository.save(pro);
        return pro;
    }

    @Override
    public void DeleteProducts(int id) {
        productRepository.deleteById(id);
    }

}
