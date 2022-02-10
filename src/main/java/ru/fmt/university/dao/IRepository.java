package ru.fmt.university.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepository<T, I> {
    T create(T toCreate);

    List<T> getAll();

    T getById(I id);

    T update(T toUpdate);

    boolean delete(I id);
}
