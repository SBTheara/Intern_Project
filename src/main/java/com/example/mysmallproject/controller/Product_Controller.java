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
public class Product_Controller {
    @Autowired
    private Products_Service productsService;
    @Autowired
    private FileDataService fileDataService;
    @Autowired
    private Product_Repository productRepository;
    @PostMapping(value = "/addNewProducts")
    public ResponseEntity<Products> saveProducts(@Valid @RequestBody Products products){
        return new ResponseEntity<>(productsService.saveProduct(products),HttpStatus.CREATED);
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Products>> getAllProducts(){
        return new ResponseEntity<>(productsService.getAllProducts(),HttpStatus.OK);
    }
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<Products> getProductByID (@PathVariable("id") int id){
        return new ResponseEntity<>(productsService.getById(id),HttpStatus.OK);
    }
    @PutMapping(value = "/updateById/{id}")
    public ResponseEntity<Products> updateProduct(@RequestBody Products products,@PathVariable("id") int id){
        return new ResponseEntity<>(productsService.updateProduct(products,id),HttpStatus.OK);
    }
    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") int id){
        productsService.deleteProducts(id);
        return new ResponseEntity<>("Delete successfull",HttpStatus.OK);
    }
    @PostMapping("/images/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(name = "image") MultipartFile file) throws IOException {
        File_Image fileImage= fileDataService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(fileImage.getName());
    }
    @GetMapping("/images/get/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "filename") String filename) throws IOException {
        byte[] img = fileDataService.downloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(img);
    }
    @DeleteMapping("/image/delete/{filename}")
    public void deleteFile(@PathVariable("filename") String filename) throws IOException {
        fileDataService.deletefile(filename);
    }
    @GetMapping(value = "/pagination")
    public ResponseEntity<Page<Products>> getProductWithPagination(@RequestParam(name = "offset") int offset ,
                                                                   @RequestParam(name= "pagesize") int pagesize){
        return new ResponseEntity<>(productsService.getProductsByPaginations(offset,pagesize),HttpStatus.OK);
    }
    @GetMapping(value = "/paginationAndSort")
    public ResponseEntity<Page<Products>> getProductWithPaginationAndSort   (
            @RequestParam(value = "field") String field,
            @RequestParam(value = "offset") int offset,
            @RequestParam(value = "pageSize") int pagesize
    ){
        return new ResponseEntity<>(productsService.getProductsByPaginationsAndSort(offset,pagesize,field),HttpStatus.OK);
    }
//    @GetMapping(value = "/search")
//    public Page<Products> GetProductBySearchIDOrName(
//            @RequestParam(value = "field") String field,
//            @RequestParam(value = "offset") int offset,
//            @RequestParam(value = "pagesize") int pagesize
//            )
//    {
//        Pageable pageable = PageRequest.of(offset,pagesize);
//        return productRepository.findAll(where(ProductSpecifications.search(field)),pageable);
//    }
    @GetMapping(value = "/filterAndSearch",consumes = {MediaType.ALL_VALUE})
    public Page<Products> filter(
            @RequestParam(name = "minPrice",required = false,defaultValue = "0") String minPrice,
            @RequestParam(name = "maxPrice",required = false,defaultValue = "0") String maxPrice,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "sortBy",required = false,defaultValue = "name") String sortby,
            @RequestParam(name = "offset",required = false,defaultValue = "0") int offset,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pagesize
    )
    {
        Pageable pageable = PageRequest.of(offset,pagesize,Sort.by(Sort.Direction.ASC,sortby));
        if(minPrice.equals("0")&&maxPrice.equals("0")){
            return productRepository.findAll(pageable);
        }
        else if(maxPrice.equals("0")){
            return productRepository.findAll(ProductSpecifications.filterMin(minPrice,search),pageable);
        }
        else {
            return productRepository.findAll(ProductSpecifications.filterMaxAndMin(minPrice, maxPrice, search), pageable);
        }
    }
}
