package com.intern.project.dto;

import lombok.*;

import java.util.List;
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
