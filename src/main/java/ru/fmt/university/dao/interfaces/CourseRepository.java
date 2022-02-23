package ru.fmt.university.dao.interfaces;

import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.GenericRepository;
import ru.fmt.university.model.entity.CourseEntity;

import java.util.List;

@Repository
public interface CourseRepository extends GenericRepository<CourseEntity, Integer> {
    List<CourseEntity> findByGroups_id(Integer groupId);
}
