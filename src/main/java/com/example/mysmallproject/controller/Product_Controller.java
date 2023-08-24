package com.example.mysmallproject.controller;
import com.example.mysmallproject.entity.File_Image;
import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.repository.Product_Repository;
import com.example.mysmallproject.service.FileDataService;
import com.example.mysmallproject.service.Products_Service;
import com.example.mysmallproject.specifications.ProductSpecifications;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@CrossOrigin(origins = {"http://localhost:4200", "http://10.0.2.2:8080","http://127.0.0.1:5500"})
@RestController
@RequestMapping(value = "/products")
@Validated
public class Product_Controller {
    @Autowired
    private Products_Service productsService;
    @Autowired
    private FileDataService fileDataService;
    @Autowired
    private Product_Repository productRepository;
    @PostMapping(value = "/addnewproducts")
    public ResponseEntity<Products> SaveProducts(@Valid @RequestBody Products products) throws IOException {
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
    @GetMapping(value = "/images/{image}")
    public ResponseEntity<byte[]> downloadFileFromDB(@PathVariable(name = "image") String filename) throws IOException {
        byte[] imageData= productsService.DownloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable(name = "id") int id){
        productsService.DeleteProducts(id);
        return new ResponseEntity<>("Delete successfull",HttpStatus.OK);
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
//    @GetMapping(value = "/searchByname")
//    public ResponseEntity<List<Products>> GetProductBySearchName(@RequestParam(value = "query") String field){
//        return ResponseEntity.ok(productsService.SearchProductByName(field));
//    }
//
//    @GetMapping(value = "/searchByID")
//    public ResponseEntity<List<Products>> GetProductBySearchID(@RequestParam(value = "query") int id){
//        return ResponseEntity.ok(productsService.SearchProductByID(id));
//    }

    @GetMapping(value = "/search/{field}")
    public List<Products> GetProductBySearchIDOrName(@PathVariable(value = "field") String field){
        return productRepository.findAll(where(ProductSpecifications.getallbyfield(field)));
    }

}
