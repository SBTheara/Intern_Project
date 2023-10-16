package com.intern.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CategoryIdsRequest {
  private List<Long> productIds = new ArrayList<>();
}
