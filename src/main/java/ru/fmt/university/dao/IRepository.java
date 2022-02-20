package ru.fmt.university.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IRepository<T, I> {
    T save(T toCreate);

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(I id);

    void deleteById(I id);
}
