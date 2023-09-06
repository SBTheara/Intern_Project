package com.example.mysmallproject.service;

import com.example.mysmallproject.dto.ProductCreationDTO;
import com.example.mysmallproject.dto.ProductDTO;
import com.example.mysmallproject.entity.Product;
import com.example.mysmallproject.repository.ProductRepository;
import com.example.mysmallproject.component.ProductSpecification;
import com.example.mysmallproject.component.ProductDTOConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements HelperGenerics<ProductDTO, ProductCreationDTO> {
  private final ProductRepository productRepository;
  private final ProductDTOConverter productDTOConverter;
  private final ModelMapper modelMapper;

  @Override
  public ProductDTO save(ProductCreationDTO productCreationDTO) {
    try {
      Product productRequest = productDTOConverter.convertProductDTOtoProduct(productCreationDTO);
      Product product = productRepository.save(productRequest);
      log.debug("Product has been add successful !!!");
      return productDTOConverter.convertProductToProductDTO(product);
    } catch (IllegalStateException exception) {
      log.error("Failed to add new product");
      return null;
    }
  }

  @Override
  public ProductDTO update(ProductCreationDTO productCreationDTO, long id) {
    try {
      Product pro = productRepository.findById(id).get();
      pro = this.modelMapper.map(productCreationDTO, pro.getClass());
      productRepository.save(pro);
      log.debug("This product who id is {} was updated.......", id);
      return modelMapper.map(pro, ProductDTO.class);
    } catch (IllegalStateException exception) {
      log.error("Could not update this product");
      return null;
    }
  }

  public Page<ProductDTO> filterAndSearch(
      double minPrice, double maxPrice, String search, String sortBy, int offset, int pageSize) {
    Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
    try {
      List<ProductDTO> pro =
          productRepository
              .findAll(ProductSpecification.filterMaxAndMin(minPrice, maxPrice, search), pageable)
              .stream()
              .map(productDTOConverter::convertProductToProductDTO)
              .toList();
      log.debug("Product found !!!!");
      return new PageImpl<>(pro, Pageable.unpaged(), pro.size());
    } catch (IllegalStateException exception) {
      log.error("Product not found !!!!");
      return null;
    }
  }

  @Override
  public void delete(long id) {
    try {
      productRepository.deleteById(id);
      log.debug("This user was deleted......");
    } catch (IllegalStateException exception) {
      log.error("Something went wrong while deleting product");
    }
  }
}
