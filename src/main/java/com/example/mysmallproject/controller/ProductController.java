package com.example.mysmallproject.controller;
import com.example.mysmallproject.entity.Image;
import com.example.mysmallproject.entity.Product;
import com.example.mysmallproject.repository.ProductRepository;
import com.example.mysmallproject.service.ImageService;
import com.example.mysmallproject.service.ProductService;
import com.example.mysmallproject.specification.ProductSpecification;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping(value = "/products")
@Validated
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productsService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductRepository productRepository;
    @PostMapping(value = "/addNewProducts")
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product){
        log.debug("Products has been added");
        return new ResponseEntity<>(productsService.saveProduct(product),HttpStatus.CREATED);
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Product>> getAllProduct(){
        log.debug("Get all information of products......");
        return new ResponseEntity<>(productsService.getAllProduct(),HttpStatus.OK);
    }
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<Product> getProductByID (@PathVariable("id") int id){
        log.debug("Get successful the product has id {}",id);
        return new ResponseEntity<>(productsService.getById(id),HttpStatus.OK);
    }
    @PutMapping(value = "/updateById/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("id") int id){
        log.debug("This user who id is {} was updated.......",id);
        return new ResponseEntity<>(productsService.updateProduct(product,id),HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") int id){
        productsService.deleteProducts(id);
        log.debug("This user was deleted......");
        return new ResponseEntity<>("Delete successful",HttpStatus.OK);
    }
    @PostMapping("/image/upload")
    public ResponseEntity<?> uploadImage(@RequestParam(name = "image") MultipartFile file) throws IOException {
        Image image = imageService.uploadImage(file);
        log.debug("Image was upload to {}.....",image.getFilePath());
        return ResponseEntity.status(HttpStatus.OK).body(image.getName());
    }
    @GetMapping("/image/get/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable(name = "fileName") String fileName) throws IOException {
        byte[] img = imageService.downloadImage(fileName);
        log.debug("Downloaded image name: {}",fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(img);
    }
    @DeleteMapping("/image/delete/{fileName}")
    public void deleteFile(@PathVariable("fileName") String fileName) throws IOException {
        imageService.deleteFile(fileName);
        log.debug("Image was deleted");
    }
    @GetMapping(value = "/pagination")
    public ResponseEntity<Page<Product>> getProductWithPagination(@RequestParam(name = "offset") int offset ,
                                                                  @RequestParam(name= "pageSize") int pageSize){
        log.debug("Get data at offset: {} and pageSize: {}",offset,pageSize);
        return new ResponseEntity<>(productsService.getProductsByPagination(offset,pageSize),HttpStatus.OK);
    }
    @GetMapping(value = "/paginationAndSort")
    public ResponseEntity<Page<Product>> getProductWithPaginationAndSort   (
            @RequestParam(value = "field") String field,
            @RequestParam(value = "offset") int offset,
            @RequestParam(value = "pageSize") int pageSize
    )
    {
        log.debug("Get the data from page has offset {},pagesize {} and sortby {}",offset,pageSize,field);
        return new ResponseEntity<>(productsService.getProductsByPaginationsAndSort(offset,pageSize,field),HttpStatus.OK);
    }
    @GetMapping(value = "/filterAndSearch",consumes = {MediaType.ALL_VALUE})
    public Page<Product> filter(
            @RequestParam(name = "minPrice",required = false,defaultValue = "0") String minPrice,
            @RequestParam(name = "maxPrice",required = false,defaultValue = "0") String maxPrice,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "sortBy",required = false,defaultValue = "name") String sortBy,
            @RequestParam(name = "offset",required = false,defaultValue = "0") int offset,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize
    )
    {
        Pageable pageable = PageRequest.of(offset,pageSize,Sort.by(Sort.Direction.ASC,sortBy));
        if(minPrice.equals("0")&&maxPrice.equals("0")){
            log.debug("Get all products");
            return productRepository.findAll(pageable);
        }
        else if(maxPrice.equals("0")){
            log.debug("Get products start from {}",minPrice);
            return productRepository.findAll(ProductSpecification.filterMin(minPrice,search),pageable);
        }
        else {
            log.debug("Get products has minPrice : {} maxPrice : {} and search : {} on page at offset : {}" +
                    " pagesize : {} and sortBy : {}",minPrice,maxPrice,search,offset,pageSize,search);
            return productRepository.findAll(ProductSpecification.filterMaxAndMin(minPrice, maxPrice, search), pageable);
        }
    }
}
