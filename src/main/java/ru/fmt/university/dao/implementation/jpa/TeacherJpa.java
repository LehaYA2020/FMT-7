package ru.fmt.university.dao.implementation.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.TeacherRepository;
import ru.fmt.university.model.entity.TeacherEntity;

import javax.transaction.Transactional;

@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface TeacherJpa extends JpaRepository<TeacherEntity, Integer>, TeacherRepository {
}
