package com.intern.project.service;
import com.intern.project.component.DTOConverter;
import com.intern.project.component.ProductSpecification;
import com.intern.project.dto.ProductCreationDTO;
import com.intern.project.dto.ProductDTO;
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
public class ProductService implements HelperGenerics<ProductDTO, ProductCreationDTO> {
  private final ProductRepository productRepository;
  private final DTOConverter<Product,ProductDTO> dtoConverter;
  @Override
  public ProductDTO save(ProductCreationDTO productCreationDTO) {
    try {
      Product productRequest = dtoConverter.convertToClass(productCreationDTO,Product.class);
      Product product = productRepository.save(productRequest);
      log.debug("Product has been add successful !!!");
      return dtoConverter.convertToDTO(product, ProductDTO.class);
    } catch (IllegalStateException exception) {
      log.error("Failed to add new product");
      return null;
    }
  }
  @Override
  public ProductDTO update(ProductCreationDTO productCreationDTO, long id) {
    try {
      Product pro = productRepository.findById(id).get();
      pro.setName(productCreationDTO.getName());
      pro.setDescription(productCreationDTO.getDescription());
      pro.setQuantity(productCreationDTO.getQuantity());
      pro.setPrice(productCreationDTO.getPrice());
      pro.setCreateAt(productCreationDTO.getCreateAt());
      pro.setImage(productCreationDTO.getImage());
      productRepository.save(pro);
      log.debug("This product who id is {} was updated.......", id);
      return dtoConverter.convertToDTO(pro, ProductDTO.class);
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
              .map(p->dtoConverter.convertToDTO(p, ProductDTO.class))
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
