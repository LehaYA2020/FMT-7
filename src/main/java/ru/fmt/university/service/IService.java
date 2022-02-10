package ru.fmt.university.service;

import java.util.List;

public interface IService<T, I> {
    void create(T toCreate);

    List<T> getAll();

    T getById(I id);

    T update(T toUpdate);

    boolean delete(I id);
}
