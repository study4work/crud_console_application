package repository;

import java.io.FileNotFoundException;
import java.util.List;

public interface GenericRepositry<T, T1> {
    void save(T t);

    T findById(T1 t1) throws FileNotFoundException;

    List<T> findAll();

    void delete(T1 t1) throws FileNotFoundException;

    void update(T t) throws FileNotFoundException;
}
