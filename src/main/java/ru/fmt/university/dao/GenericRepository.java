package ru.fmt.university.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenericRepository<T, I> {
    T save(T toCreate);

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(I id);

    void deleteById(I id);
}
