package com.example.mysmallproject.controller;
import com.example.mysmallproject.dto.ProductCreationDTO;
import com.example.mysmallproject.dto.ProductDTO;
import com.example.mysmallproject.entity.Image;
import com.example.mysmallproject.entity.Product;
import com.example.mysmallproject.exception.GlobalExceptionHandler;
import com.example.mysmallproject.service.ImageService;
import com.example.mysmallproject.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
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
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productsService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    @PostMapping(value = "/addNewProducts")
    public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductCreationDTO productCreationDTO){
        try{
            log.debug("Product has been add successful !!!");
            return new ResponseEntity<>(productsService.saveProduct(productCreationDTO),HttpStatus.CREATED);
        }catch (IllegalStateException exception){
            log.error("Failed to add new product");
            throw new IllegalStateException(GlobalExceptionHandler.ERROR);
        }
    }
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        try{
            log.debug("Get all information of products......");
            return new ResponseEntity<>(productsService.getAllProduct(),HttpStatus.OK);
        }catch (IllegalStateException exception){
            log.error("Something went wrong !!!");
            throw new IllegalStateException(GlobalExceptionHandler.NOT_FOUND);
        }
    }
    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<ProductDTO> getProductByID (@PathVariable("id") int id){
        try{
            log.debug("Get successful the product has id {}",id);
            return new ResponseEntity<>(productsService.getById(id),HttpStatus.OK);
        }catch (IllegalStateException exception){
            log.error("Product not found !!!");
            throw new IllegalStateException(GlobalExceptionHandler.NOT_FOUND);
        }
    }
    @PutMapping(value = "/updateById/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductCreationDTO productCreationDTO, @PathVariable("id") int id){
        try{
            log.debug("This product who id is {} was updated.......",id);
            return new ResponseEntity<>(modelMapper.map(productsService.updateProduct(productCreationDTO,id),ProductDTO.class),HttpStatus.OK);
        }catch (IllegalStateException exception){
            log.error("Could not update this product");
            throw new IllegalStateException(exception.getMessage());
        }
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
        log.debug("Get the data from page has offset {},page-size {} and sort-by {}",offset,pageSize,field);
        return new ResponseEntity<>(productsService.getProductsByPaginationsAndSort(offset,pageSize,field),HttpStatus.OK);
    }
    @GetMapping(value = "/filterAndSearch",consumes = {MediaType.ALL_VALUE})
    public ResponseEntity<Page<ProductDTO>> filter(
            @RequestParam(name = "minPrice",required = false,defaultValue = "0") String minPrice,
            @RequestParam(name = "maxPrice",required = false,defaultValue = "0") String maxPrice,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "sortBy",required = false,defaultValue = "name") String sortBy,
            @RequestParam(name = "offset",required = false,defaultValue = "0") int offset,
            @RequestParam(name = "pageSize",required = false,defaultValue = "10") int pageSize
    )
    {
        try{
            Page<ProductDTO> productDTOS = productsService.fileterAndSearch(minPrice, maxPrice, search, sortBy, offset, pageSize);
            log.debug("Product found !!!!");
            return ResponseEntity.ok().body(productDTOS);
        }catch (IllegalStateException exception){
            log.error("Product not found !!!!");
            throw new IllegalStateException(GlobalExceptionHandler.NOT_FOUND);
        }
    }
}
