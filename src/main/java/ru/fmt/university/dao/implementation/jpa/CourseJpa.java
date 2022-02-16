package ru.fmt.university.dao.implementation.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.CourseRepository;
import ru.fmt.university.model.entity.CourseEntity;

import javax.transaction.Transactional;

@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface CourseJpa extends JpaRepository<CourseEntity, Integer>, CourseRepository {
}