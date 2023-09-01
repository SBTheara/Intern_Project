package com.example.mysmallproject.service.implement;

import com.example.mysmallproject.entity.Product;
import com.example.mysmallproject.exception.RequestException;
import com.example.mysmallproject.repository.ProductRepository;
import com.example.mysmallproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImplement implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
    @Override
    public Product getById(int id) {
        return productRepository.findById(id).orElseThrow(() -> new RequestException("Not found for this product"));
    }
    @Override
    public Product updateProduct(Product product, int id) {
        Product pro;
        pro = productRepository.findById(id).orElseThrow(() -> new RequestException("Not found for this product"));
        pro.setId(product.getId());
        pro.setName(product.getName());
        pro.setDescription(product.getDescription());
        pro.setQuantity(product.getQuantity());
        pro.setPrice(product.getPrice());
        pro.setCreateAt(product.getCreateAt());
        pro.setImage(product.getImage());
        productRepository.save(pro);
        return pro;
    }
    @Override
    public Page<Product> getProductsByPagination(int offset, int pagesize) {
        Pageable pageable = PageRequest.of(offset, pagesize);
        return productRepository.findAll(pageable);
    }
    @Override
    public Page<Product> getProductsByPaginationsAndSort(int offset, int pagesize, String field) {
        return productRepository.findAll(PageRequest.of(offset,pagesize).withSort(Sort.by(field)));
    }
    @Override
    public void deleteProducts(int id) {
        productRepository.deleteById(id);
    }

}
