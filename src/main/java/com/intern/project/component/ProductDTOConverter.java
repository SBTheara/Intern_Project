package com.intern.project.component;

import com.intern.project.dto.ProductCreationDTO;
import com.intern.project.dto.ProductDTO;
import com.intern.project.entity.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ProductDTOConverter {
    private final ModelMapper modelMapper;
    public ProductDTO convertProductToProductDTO (Product product){
        return modelMapper.map(product,ProductDTO.class);
    }
    public Product convertProductDTOtoProduct(ProductCreationDTO productCreationDTO){
        return modelMapper.map(productCreationDTO,Product.class);
    }
}
