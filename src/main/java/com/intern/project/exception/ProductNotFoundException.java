package com.intern.project.exception;

public class ProductNotFoundException extends IllegalStateException{
    public ProductNotFoundException(Long id) {
        super("Product not found for id "+ id);
    }
}
