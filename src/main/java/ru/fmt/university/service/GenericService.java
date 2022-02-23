package ru.fmt.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericService<T, I> {
    T create(T toCreate);

    Page<T> getAll(Pageable pageable);

    T getById(I id);

    T update(T toUpdate);

    boolean deleteById(I id);
}
