package com.example.mysmallproject.service;

import com.example.mysmallproject.dto.ProductCreationDTO;
import com.example.mysmallproject.dto.ProductDTO;
import com.example.mysmallproject.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDTO saveProduct(ProductCreationDTO productCreationDTO);
    List<ProductDTO> getAllProduct();
    ProductDTO getById(int id);
    ProductDTO updateProduct(ProductCreationDTO productCreationDTO, int id);
    Page<Product> getProductsByPagination(int offset, int pagesize);
    Page<Product> getProductsByPaginationsAndSort(int offset, int pagesize, String field);
    Page<ProductDTO> fileterAndSearch (String minPrice,String maxPrice,String search,String sortBy,int offset,int pageSize);
    void deleteProducts(int id);

}
