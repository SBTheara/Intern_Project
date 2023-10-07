package com.intern.project.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
  private long id;
  private String address;
  private String orderDate;
  private String status;
  private String createAt;
  private List<ProductResponse> products = new ArrayList<>();
}
