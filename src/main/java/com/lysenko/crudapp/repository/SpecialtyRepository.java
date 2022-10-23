package com.lysenko.crudapp.repository;

public interface SpecialtyRepository<T> {
    void save(T t);

    T find();

    void update(T t);

    void delete();
}

