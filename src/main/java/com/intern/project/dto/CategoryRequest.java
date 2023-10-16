package com.intern.project.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

  private String name;
  private String details;
  private List<Long> productIds = new ArrayList<>();
}
