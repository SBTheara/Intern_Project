package com.intern.project.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
  private Long id;
  private String address;
  private String orderDate;
  private String status;
  private String createAt;
  private int totalProducts;
  private List<Long> productIds = new ArrayList<>();
}
