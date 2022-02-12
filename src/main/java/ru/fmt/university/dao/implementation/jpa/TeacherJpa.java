package ru.fmt.university.dao.implementation.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.ITeacherRepository;
import ru.fmt.university.model.dto.Teacher;
import ru.fmt.university.model.entity.TeacherEntity;

@Repository
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface TeacherJpa extends CrudRepository<TeacherEntity, Integer>, ITeacherRepository {
}
