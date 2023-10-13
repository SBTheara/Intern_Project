package com.intern.project.utils;

import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageRequest {
  private int page;
  private int size;
  private String direction;
  private String sort;

  public Pageable toPageable() {
    return org.springframework.data.domain.PageRequest.of(
        page, size, Sort.by(Sort.Direction.fromString(direction), sort));
  }
}
