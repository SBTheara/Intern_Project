package com.intern.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private Long productId;
    private int quantity;
    private String description;
}
