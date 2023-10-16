package com.intern.project.exception;

public class CategoryNotFoundException extends IllegalStateException{
    public CategoryNotFoundException(Long id) {
        super("Category not found for id : "+ id);
    }
}
