package com.intern.project.service;
public interface HelperGenerics<T,C>{
    T save(C c);
    T update(C c, long id);
    void delete(long id);
}
