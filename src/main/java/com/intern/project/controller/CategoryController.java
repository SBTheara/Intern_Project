package com.intern.project.controller;

import com.intern.project.dto.CategoryIdsRequest;
import com.intern.project.dto.CategoryRequest;
import com.intern.project.dto.CategoryResponse;
import com.intern.project.dto.CategoryResponses;
import com.intern.project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
    return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<CategoryResponses> listCategoryResponse() {
    return new ResponseEntity<>(categoryService.listCategory(), HttpStatus.OK);
  }

  @PostMapping("/{id}")
  public ResponseEntity<CategoryResponse> createProuductInCategory(
      @PathVariable Long id, @RequestBody CategoryIdsRequest request) {
    return new ResponseEntity<>(
        categoryService.createProductInCategory(id, request), HttpStatus.OK);
  }
}
