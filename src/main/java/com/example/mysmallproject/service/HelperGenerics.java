package com.example.mysmallproject.service;
import org.springframework.data.domain.Page;

public interface HelperGenerics<T,C>{
    T save(C c);
    T update(C c, int id);
    void delete(int id);
}
