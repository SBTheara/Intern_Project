package com.intern.project.controller;

import com.intern.project.dto.PageResponse;
import com.intern.project.dto.ProductRequest;
import com.intern.project.dto.ProductResponse;
import com.intern.project.entity.Image;
import com.intern.project.service.ImageService;
import com.intern.project.service.ProductService;
import com.intern.project.utils.PageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/v1/products")
@Validated
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
  private final ProductService productsService;
  private final ImageService imageService;
  private final ModelMapper modelMapper;

  @PostMapping(value = "/add-new-product")
  public ResponseEntity<ProductResponse> saveProduct(
      @Valid @RequestBody ProductRequest productRequest) {
    return new ResponseEntity<>(productsService.createProduct(productRequest), HttpStatus.CREATED);
  }

  @PutMapping(value = "/update-by-id/{id}")
  public ResponseEntity<ProductResponse> updateProduct(
      @Valid @RequestBody ProductRequest productRequest, @PathVariable("id") Long id) {
    return new ResponseEntity<>(
        modelMapper.map(productsService.updateProduct(productRequest, id), ProductResponse.class),
        HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete-by-id/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") int id) {
    productsService.delete(id);
    return new ResponseEntity<>("Delete successful", HttpStatus.OK);
  }

  @PostMapping(value = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImage(@RequestParam(name = "image") MultipartFile file)
      throws IOException {
    Image image = imageService.uploadImage(file);
    return ResponseEntity.status(HttpStatus.OK).body(image.getName());
  }

  @GetMapping("/image/get/{filename}")
  public ResponseEntity<byte[]> downloadImage(@PathVariable(name = "filename") String fileName)
      throws IOException {
    byte[] img = imageService.downloadImage(fileName);
    return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(img);
  }

  @DeleteMapping("/image/delete/{filename}")
  public void deleteFile(@PathVariable("filename") String fileName) throws IOException {
    imageService.deleteFile(fileName);
  }

  @GetMapping(value = "/listProduct")
  public ResponseEntity<PageResponse<ProductResponse>> filter(
      @RequestParam(required = false, defaultValue = "desc") String direction,
      @RequestParam(required = false, defaultValue = "id") String sortBy,
      @RequestParam(required = false, defaultValue = "1") int offset,
      @RequestParam(required = false, defaultValue = "10") int pageSize,
      @RequestParam(required = false) Double minPrice,
      @RequestParam(required = false) Double maxPrice,
      @RequestParam(required = false) String search) {
    PageRequest request =
        PageRequest.builder()
            .page(offset - 1)
            .size(pageSize)
            .direction(direction)
            .sort(sortBy)
            .build();
    Page<ProductResponse> productResponsePage =
        productsService.filterAndSearch(minPrice, maxPrice, search, request);
    return new ResponseEntity<>(
        new PageResponse<>(
            productResponsePage.getNumber(),
            productResponsePage.getSize(),
            productResponsePage.getTotalElements(),
            productResponsePage.getContent()),
        HttpStatus.OK);
  }
}
