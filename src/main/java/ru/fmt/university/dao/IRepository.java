package ru.fmt.university.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepository<T, I> {
    T save(T toCreate);

    List<T> findAll();

    T getById(I id);

    void deleteById(I id);
}
