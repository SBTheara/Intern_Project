package com.intern.project.dto;

import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageResponse<T> {
  private int page;
  private int size;
  private long total;
  private List<T> content;
}
