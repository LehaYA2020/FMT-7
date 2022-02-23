package ru.fmt.university.dao.implementation.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.CourseRepository;
import ru.fmt.university.model.entity.CourseEntity;

import javax.transaction.Transactional;

@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface CourseJpa extends CrudRepository<CourseEntity, Integer>, CourseRepository {
}