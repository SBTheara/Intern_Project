package com.example.mysmallproject.controller;
import com.example.mysmallproject.entity.Products;
import com.example.mysmallproject.repository.Product_Repository;
import com.example.mysmallproject.service.FileDataService;
import com.example.mysmallproject.service.Products_Service;
import com.example.mysmallproject.specifications.ProductSpecifications;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return new ResponseEntity<>(productsService.GetAllProducts(),HttpStatus.OK);
    }
    @GetMapping(value = "/images/{image}")
    public ResponseEntity<byte[]> downloadFileFromDB(@PathVariable(name = "image") String filename) throws IOException {
        byte[] img = fileDataService.downloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(img);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> DeleteProduct(@PathVariable(name = "id") int id){
        productsService.DeleteProducts(id);
        return new ResponseEntity<>("Delete successfull",HttpStatus.OK);
    }
    @GetMapping(value = "/pagination")
    public ResponseEntity<Page<Products>> GetProductWithPagination(@RequestParam(name = "offset") int offset ,
                                                                   @RequestParam(name= "pagesize") int pagesize){
        return new ResponseEntity<>(productsService.GetProductsByPaginations(offset,pagesize),HttpStatus.OK);
    }
    @GetMapping(value = "/pagiationandsort")
    public ResponseEntity<Page<Products>> GetProductWithPaginationAndSort   (
            @RequestParam(value = "field") String field,
            @RequestParam(value = "offset") int offset,
            @RequestParam(value = "pagesize") int pagesize
    ){
        return new ResponseEntity<>(productsService.GetProductsByPaginationsAndSort(offset,pagesize,field),HttpStatus.OK);
    }
    @GetMapping(value = "/search")
    public Page<Products> GetProductBySearchIDOrName(
            @RequestParam(value = "field") String field,
            @RequestParam(value = "offset") int offset,
            @RequestParam(value = "pagesize") int pagesize
            )
    {
        Pageable pageable = PageRequest.of(offset,pagesize);
        return productRepository.findAll(where(ProductSpecifications.search(field)),pageable);
    }
    @GetMapping(value = "/filter",consumes = {MediaType.ALL_VALUE})
    public Page<Products> filter(

            @RequestParam(name = "minPrice",required = false,defaultValue = "0") String minPrice,
            @RequestParam(name = "maxPrice",required = false,defaultValue = "0") String maxPrice,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "sortby",required = false,defaultValue = "name") String sortby,
            @RequestParam(name = "offset",required = false,defaultValue = "0") int offset,
            @RequestParam(name = "pagesize",required = false,defaultValue = "10") int pagesize
    )
    {
        Pageable pageable = PageRequest.of(offset,pagesize,Sort.by(Sort.Direction.ASC,sortby));
        if(minPrice.equals("0")&&maxPrice.equals("0")){
            return productRepository.findAll(pageable);
        }
        if(maxPrice.equals("0")){
            return productRepository.findAll(ProductSpecifications.filterMin(minPrice,search),pageable);
        }
        else {
            return productRepository.findAll(ProductSpecifications.filterMaxAndMin(minPrice, maxPrice, search), pageable);
        }
    }
}
