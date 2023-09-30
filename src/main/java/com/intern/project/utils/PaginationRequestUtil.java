package com.intern.project.utils;

import com.intern.project.dto.PaginateRequest;
import com.intern.project.exception.PaginateRequestException;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PaginationRequestUtil {
    public static void validateRequest (PaginateRequest request, List<String> ALLOWED_FIELDS){
        if(request.getPage()<0){
            throw new PaginateRequestException("Invalid page number");
        }
        if(request.getSize()<0){
            throw new PaginateRequestException("Invalid page size");
        }
        if(!ALLOWED_FIELDS.contains(request.getSortBy())){
            throw new PaginateRequestException("Invalid sort field");
        }
        if(!List.of("asc","desc").contains(request.getDirection().toLowerCase())){
            throw new PaginateRequestException("Invalid sort direction");
        }
    }
}
