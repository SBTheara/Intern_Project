package com.example.mysmallproject.controller;
import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.service.Products_Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.executable.ValidateOnExecution;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@ValidateOnExecution
@Validated
public class Product_Controller {
    @Autowired
    private Products_Service productsService;
    @PostMapping(value = "/addnew")
    public ResponseEntity<Products> SaveProducts(
            @Valid @RequestParam("file") MultipartFile file,
            @Valid @RequestParam("name")  String name,
            @Valid @RequestParam("description")String description,
            @Valid @RequestParam("category_id")int category_id,
            @Valid @RequestParam("quantity") int quantity,
            @Valid @RequestParam("price")double price,
            @Valid @RequestParam("create_at") Date create_at
        ) throws IOException {
        return new ResponseEntity<Products>((Products)productsService.SaveProduct(file,name,description,quantity,price,create_at),
        HttpStatus.CREATED);
    }
    @GetMapping(value = "/getall")
    public ResponseEntity<List<Products>> GetAllProducts(){
        return new ResponseEntity<List<Products>>(productsService.GetAllProducts(),HttpStatus.OK);
    }
    @GetMapping(value = "images/{image}")
    public ResponseEntity<byte[]> downloadFileFromDB(@PathVariable(name = "image") String filename) throws IOException {
        byte[] imageData= productsService.DownloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Products> UpdateProduct(
            @PathVariable("id") int id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("name")String name,
            @RequestParam("description")String description,
            @RequestParam("quantity")int quantity,
            @RequestParam("price")double price,
            @RequestParam("create_at") Date create_at
        ) throws IOException {
        return new ResponseEntity<Products>(productsService.UpdateProducts(id,file,name,description,quantity,price,create_at),HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable(name = "id") int id){
        productsService.DeleteProducts(id);
        return new ResponseEntity<String>("Delete successfull",HttpStatus.OK);
    }
}
