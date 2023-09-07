package com.intern.project.controller;
import com.intern.project.dto.ProductCreationDTO;
import com.intern.project.dto.ProductDTO;
import com.intern.project.entity.Image;
import com.intern.project.entity.Product_;
import com.intern.project.service.ImageService;
import com.intern.project.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@RestController
@RequestMapping(value = "/products")
@Validated
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productsService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    @PostMapping(value = "/add-new-product")
    public ResponseEntity<ProductDTO> saveProduct(
            @Valid @RequestBody ProductCreationDTO productCreationDTO) {
        return new ResponseEntity<>(productsService.save(productCreationDTO), HttpStatus.CREATED);
    }
    @PutMapping(value = "/update-by-id/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @Valid @RequestBody ProductCreationDTO productCreationDTO, @PathVariable("id") int id) {
        return new ResponseEntity<>(
                modelMapper.map(productsService.update(productCreationDTO, id), ProductDTO.class),
                HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete-by-id/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") int id) {
        productsService.delete(id);
        return new ResponseEntity<>("Delete successful", HttpStatus.OK);
    }
    @PostMapping("/image/upload")
    public ResponseEntity<?> uploadImage(@RequestParam(name = "image") MultipartFile file)
            throws IOException {
        Image image = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK).body(image.getName());
    }
    @GetMapping("/image/get/{filename}")
    public ResponseEntity<?> downloadImage(@PathVariable(name = "filename") String fileName)
            throws IOException {
        byte[] img = imageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(img);
    }
    @DeleteMapping("/image/delete/{filename}")
    public void deleteFile(@PathVariable("filename") String fileName) throws IOException {
        imageService.deleteFile(fileName);
    }
    @GetMapping(value = "/filter-and-search")
    public ResponseEntity<Page<ProductDTO>> filter(
            @RequestParam(name = "min-price", required = false, defaultValue = "0") double minPrice,
            @RequestParam(name = "max-price", required = false, defaultValue = "0") double maxPrice,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "sort-by", required = false, defaultValue = Product_.ID) String sortBy,
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(name = "page-size", required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok().body(productsService.filterAndSearch(minPrice, maxPrice, search, sortBy, offset, pageSize));
    }
}