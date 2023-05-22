package com.lysenko.crudapp.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T save(T t);

    T findById(ID id);

    List<T> findAll();

    void delete(ID id);

    T update(T t);
}
