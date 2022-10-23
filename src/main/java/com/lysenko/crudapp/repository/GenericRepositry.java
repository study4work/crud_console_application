package com.lysenko.crudapp.repository;

import java.io.FileNotFoundException;
import java.util.List;

public interface GenericRepositry<T, ID> {
    T save(T t);

    T findById(ID id) throws FileNotFoundException;

    List<T> findAll();

    void delete(ID id) throws FileNotFoundException;

    T update(T t) throws FileNotFoundException;
}
