package com.intern.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private String details;
    private List<Long> productIds = new ArrayList<>();
}
