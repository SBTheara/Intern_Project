package com.intern.project.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ParameterObject
public class ProductyFilterRequest {
  @Parameter(in = ParameterIn.QUERY)
  private Double minPrice;

  @Parameter(in = ParameterIn.QUERY)
  private Double maxPrice;

  @Parameter(in = ParameterIn.QUERY)
  private String search;
}
