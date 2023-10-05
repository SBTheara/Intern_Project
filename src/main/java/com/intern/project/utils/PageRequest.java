package com.intern.project.utils;

import lombok.*;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageRequest{
    private int page;
    private int size;
    private Sort.Direction direction;
    private Sort sort;
}
