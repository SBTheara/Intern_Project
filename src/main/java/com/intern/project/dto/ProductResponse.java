package com.intern.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
  private int id;
  private String name;
  private String description;
  private int quantity;
  private double price;
  private boolean isActive;
  private String image;
}
