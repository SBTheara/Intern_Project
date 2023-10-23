package com.intern.project.service;

import com.intern.project.dto.ProductRequest;
import com.intern.project.dto.ProductResponse;
import com.intern.project.entity.Product;
import com.intern.project.entity.Product_;
import com.intern.project.exception.ProductBadRequesException;
import com.intern.project.exception.ProductNotFoundException;
import com.intern.project.repository.ProductRepository;
import com.intern.project.utils.PageRequest;
import com.intern.project.utils.ProductSpecification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;

  @Transactional(rollbackFor = Exception.class)
  public ProductResponse createProduct(ProductRequest request) {
    Product product = new Product();
    this.modelMapper.map(request, product);
    log.info("Product create successfully !!! ");
    return this.modelMapper.map(productRepository.save(product), ProductResponse.class);
  }

  @Transactional(rollbackFor = Exception.class)
  public ProductResponse updateProduct(ProductRequest request, Long id) {
    try {
      Product product =
          productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
      product.setId(id);
      this.modelMapper.map(request, product);
      log.info("Product update successfully !!! ");
      return this.modelMapper.map(product, ProductResponse.class);
    } catch (Exception exception) {
      log.error("Could not update this product");
      throw new ProductBadRequesException("Could not update product");
    }
  }

  public Page<ProductResponse> listProducts(
      Double minPrice, Double maxPrice, String search, PageRequest request) {
    Pageable pageable = request.toPageable();
    Specification<Product> specification = Specification.where(null);
    specification = ProductSpecification.search(specification, search);
    specification =
        ProductSpecification.withFilterFieldHas(specification, Product_.PRICE, minPrice, maxPrice);
    Page<Product> productsPage = productRepository.findAll(specification, pageable);
    List<ProductResponse> productResponseList =
        productsPage.getContent().stream()
            .map(product -> this.modelMapper.map(product, ProductResponse.class))
            .toList();
    return new PageImpl<>(productResponseList, pageable, productsPage.getTotalElements());
  }

  public void delete(long id) {
    Product product =
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    log.debug("This user was deleted......");
    productRepository.delete(product);
  }
}
