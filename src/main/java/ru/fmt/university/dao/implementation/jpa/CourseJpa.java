package ru.fmt.university.dao.implementation.jpa;

import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.ICourseRepository;
import ru.fmt.university.model.entity.CourseEntity;

@Repository
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface CourseJpa extends CrudRepository<CourseEntity, Integer>, ICourseRepository {
}
