package com.example.mysmallproject.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

@Setter
@Getter
public class PageDTO {
    private int offset;
    private int pagesize;
    public Pageable getPageable(int offset,int pagesize){
        Integer pageNo = Objects.nonNull(offset)?getOffset():this.offset;
        Integer pageSize = Objects.nonNull(pagesize)?getPagesize():this.pagesize;
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        return pageRequest;
    }
}
