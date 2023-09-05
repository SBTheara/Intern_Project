package com.example.mysmallproject.exception;

import java.util.NoSuchElementException;

public class ProductNotFoundException extends NoSuchElementException {

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
