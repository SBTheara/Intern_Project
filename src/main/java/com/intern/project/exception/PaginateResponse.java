package com.intern.project.exception;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginateResponse<T> {
    private long total;
    private int page;
    private int size;
    private List<T> content;
}
