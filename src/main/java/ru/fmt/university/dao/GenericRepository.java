package ru.fmt.university.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;

@org.springframework.stereotype.Repository
@Transactional
public interface GenericRepository<T, I> {
    T save(T toCreate);

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(I id);

    void deleteById(I id);
}
