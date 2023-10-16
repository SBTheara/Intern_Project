package com.intern.project.service;

import com.intern.project.dto.CategoryIdsRequest;
import com.intern.project.dto.CategoryRequest;
import com.intern.project.dto.CategoryResponse;
import com.intern.project.dto.CategoryResponses;
import com.intern.project.entity.Category;
import com.intern.project.entity.Product;
import com.intern.project.exception.CategoryNotFoundException;
import com.intern.project.exception.ProductNotFoundException;
import com.intern.project.repository.CategoryRepository;
import com.intern.project.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;

  public CategoryResponse createCategory(CategoryRequest request) {
    Category category = new Category();
    List<Product> productList =
        request.getProductIds().stream()
            .map(
                productRequestId ->
                    productRepository
                        .findById(productRequestId)
                        .orElseThrow(() -> new ProductNotFoundException(productRequestId)))
            .toList();
    category.setProducts(productList);
    this.modelMapper.map(request, category);
    return prepareListResponse(categoryRepository.save(category));
  }

  public CategoryResponses listCategory() {
    CategoryResponses categoryResponses = new CategoryResponses();
    categoryResponses.setCategoryResponses(
        categoryRepository.findAll().stream().map(this::prepareListResponse).toList());
    return categoryResponses;
  }

  private CategoryResponse prepareListResponse(Category category) {
    CategoryResponse categoryResponse = new CategoryResponse();
    List<Long> productList = category.getProducts().stream().map(Product::getId).toList();
    categoryResponse.setProductIds(productList);
    this.modelMapper.map(category, categoryResponse);
    return categoryResponse;
  }

  public CategoryResponse createProductInCategory(Long id, CategoryIdsRequest request) {
    Category category =
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    List<Product> productList =
        request.getProductIds().stream()
            .map(
                productId ->
                    productRepository
                        .findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(productId)))
            .toList();
    category.getProducts().addAll(productList);
    return prepareListResponse(categoryRepository.save(category));
  }
}
