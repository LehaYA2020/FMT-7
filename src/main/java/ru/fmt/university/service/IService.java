package ru.fmt.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IService<T, I> {
    void create(T toCreate);

    Page<T> getAll(Pageable pageable);

    T getById(I id);

    T update(T toUpdate);

    boolean deleteById(I id);
}
