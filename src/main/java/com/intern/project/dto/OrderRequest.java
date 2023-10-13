package com.intern.project.dto;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
  private String address;
  private String orderDate;
  private String status;
  private String createAt;
  private List<Long> productIds = new ArrayList<>();
}
