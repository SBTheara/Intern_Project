package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.dto.ProductCreationDTO;
import com.example.mysmallproject.dto.ProductDTO;
import com.example.mysmallproject.entity.Product;
import com.example.mysmallproject.exception.GlobalExceptionHandler;
import com.example.mysmallproject.repository.ProductRepository;
import com.example.mysmallproject.service.ProductService;
import com.example.mysmallproject.specification.ProductSpecification;
import com.example.mysmallproject.utils.ProductDTOConverter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImplement implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDTOConverter productDTOConverter;
    private final ModelMapper modelMapper;
    @Override
    public ProductDTO saveProduct(ProductCreationDTO productCreationDTO) {
        Product productRequest = productDTOConverter.convertProductDTOtoProduct(productCreationDTO);
        Product product = productRepository.save(productRequest);
        return productDTOConverter.convertProductToProductDTO(product);
    }
    @Override
    public List<ProductDTO> getAllProduct() {
        return productRepository
                .findAll()
                .stream()
                .map(productDTOConverter::convertProductToProductDTO)
                .collect(Collectors.toList());
    }
    @Override
    public ProductDTO getById(int id) {
        Product pro = productRepository.findById(id).orElseThrow(()->new IllegalStateException(GlobalExceptionHandler.NOT_FOUND));
        return modelMapper.map(pro,ProductDTO.class);
    }
    @Override
    public ProductDTO updateProduct(ProductCreationDTO productCreationDTO, int id) {
        Product pro;
        pro = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Not found for this product"));
        pro.setName(productCreationDTO.getName());
        pro.setDescription(productCreationDTO.getDescription());
        pro.setQuantity(productCreationDTO.getQuantity());
        pro.setPrice(productCreationDTO.getPrice());
        pro.setCreateAt(productCreationDTO.getCreateAt());
        pro.setImage(productCreationDTO.getImage());
        productRepository.save(pro);
        return modelMapper.map(pro,ProductDTO.class);
    }
    @Override
    public Page<Product> getProductsByPagination(int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset, pageSize);
        return productRepository.findAll(pageable);
    }
    @Override
    public Page<Product> getProductsByPaginationsAndSort(int offset, int pageSize, String field) {
        return productRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
    }
    @Override
    public Page<ProductDTO> fileterAndSearch(String minPrice, String maxPrice, String search, String sortBy, int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset,pageSize,Sort.by(Sort.Direction.ASC,sortBy));
        if(minPrice.equals("0")&&maxPrice.equals("0")){
            List<ProductDTO> pro =productRepository.findAll(pageable).stream().map(productDTOConverter::convertProductToProductDTO).toList();
            return new PageImpl<>(pro,Pageable.unpaged(),pro.size());
        }
        else if(maxPrice.equals("0")){
            List<ProductDTO> pro = productRepository.findAll(ProductSpecification.filterMin(minPrice,search),pageable).stream().map(productDTOConverter::convertProductToProductDTO).collect(Collectors.toList());
            return new PageImpl<>(pro,Pageable.unpaged(),pro.size());
        }
        else {
            List<ProductDTO> pro = productRepository.findAll(ProductSpecification.filterMaxAndMin(minPrice, maxPrice, search), pageable).stream().map(productDTOConverter::convertProductToProductDTO).toList();
            return new PageImpl<>(pro,Pageable.unpaged(),pro.size());
        }
    }
    @Override
    public void deleteProducts(int id) {
        productRepository.deleteById(id);
    }

}
