package com.intern.project.service;
import com.intern.project.component.DTOConverter;
import com.intern.project.utils.ProductSpecification;
import com.intern.project.dto.ProductRequest;
import com.intern.project.dto.ProductResponse;
import com.intern.project.repository.ProductRepository;
import com.intern.project.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements HelperGenerics<ProductResponse, ProductRequest> {
  private final ProductRepository productRepository;
  private final DTOConverter<Product, ProductResponse> dtoConverter;
  @Override
  public ProductResponse save(ProductRequest productCreationDTO) {
      Product productRequest = dtoConverter.convertToClass(productCreationDTO,Product.class);
      Product product = productRepository.save(productRequest);
      log.debug("Product has been add successful !!!");
      return dtoConverter.convertToDTO(product, ProductResponse.class);
  }
  @Override
  public ProductResponse update(ProductRequest productRequest, long id) {
    try {
      Product pro = productRepository.findById(id).get();
      pro.setName(productRequest.getName());
      pro.setDescription(productRequest.getDescription());
      pro.setQuantity(productRequest.getQuantity());
      pro.setPrice(productRequest.getPrice());
      pro.setCreateAt(productRequest.getCreateAt());
      pro.setImage(productRequest.getImage());
      productRepository.save(pro);
      log.debug("This product who id is {} was updated.......", id);
      return dtoConverter.convertToDTO(pro, ProductResponse.class);
    } catch (IllegalStateException exception) {
      log.error("Could not update this product");
      return null;
    }
  }
  public Page<ProductResponse> filterAndSearch(
      double minPrice, double maxPrice, String search, String sortBy, int offset, int pageSize) {
    Pageable pageable = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
    try {
      List<ProductResponse> pro =
          productRepository
              .findAll(ProductSpecification.filterMaxAndMin(minPrice, maxPrice, search), pageable)
              .stream()
              .map(p->dtoConverter.convertToDTO(p, ProductResponse.class))
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
