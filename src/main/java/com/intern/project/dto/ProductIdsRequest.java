package com.intern.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductIdsRequest {
    private List<Long> productIds= new ArrayList<>();
}
