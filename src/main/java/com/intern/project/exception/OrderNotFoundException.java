package com.intern.project.exception;

public class OrderNotFoundException extends IllegalStateException{
    public OrderNotFoundException(Long id) {
        super("Order not found for id : "+id);
    }
}
