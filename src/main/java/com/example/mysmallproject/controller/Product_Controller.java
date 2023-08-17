package com.example.mysmallproject.controller;
import com.example.mysmallproject.entity.FileData;
import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.service.FileDataService;
import com.example.mysmallproject.service.Products_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class Product_Controller {
    @Autowired
    private Products_Service productsService;
    @Autowired
    private FileDataService fileDataService;
    @PostMapping(value = "/addnewproducts",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Products> SaveProducts(@Valid @RequestParam(name = "file") MultipartFile file ,
                                                 @Valid @ModelAttribute(name = "Products") Products products){
        try {
            FileData fileData = fileDataService.uploadFile(file);
            products.setImage(fileData.getName());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return new ResponseEntity<>(productsService.SaveProduct(products),
        HttpStatus.CREATED);
    }
    @GetMapping(value = "/getall")
    public ResponseEntity<List<Products>> GetAllProducts(){
        return new ResponseEntity<List<Products>>(productsService.GetAllProducts(),HttpStatus.OK);
    }
    @GetMapping(value = "/getallproduct/{field}")
    public ResponseEntity<List<Products>> GetAllProductsBySorting(@PathVariable(name = "field") String field){
        return new ResponseEntity<List<Products>>(productsService.GetAllProductsBySorting(field),HttpStatus.OK);
    }
    @GetMapping(value = "images/{image}")
    public ResponseEntity<byte[]> downloadFileFromDB(@PathVariable(name = "image") String filename) throws IOException {
        byte[] imageData= productsService.DownloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable(name = "id") int id){
        productsService.DeleteProducts(id);
        return new ResponseEntity<String>("Delete successfull",HttpStatus.OK);
    }
    @GetMapping(value = "/{offset}/{pagesize}")
    public ResponseEntity<Page<Products>> GetProductWithPagination(@PathVariable(name = "offset") int offset ,
                                                                   @PathVariable(name= "pagesize") int pagesize){
        return new ResponseEntity<>(productsService.GetProductsByPaginations(offset,pagesize),HttpStatus.OK);
    }

    @GetMapping(value = "/{offset}/{pagesize}/{field}")
    public ResponseEntity<Page<Products>> GetProductWithPaginationAndSort   (@PathVariable(name = "offset") int offset ,
                                                                            @PathVariable(name= "pagesize") int pagesize,
                                                                            @PathVariable(name = "field") String field

    ){
        return new ResponseEntity<>(productsService.GetProductsByPaginationsAndSort(offset,pagesize,field),HttpStatus.OK);
    }




}
