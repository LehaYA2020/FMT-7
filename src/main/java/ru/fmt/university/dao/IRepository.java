package ru.fmt.university.dao;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IRepository<T, I> {
    T save(T toCreate);

    List<T> findAll();

    Optional<T> findById(I id);

    void deleteById(I id);
}
