package com.intern.project.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaginateRequest {
    private int page;
    private int size;
    private String sortBy;
    private String direction;
}
