package ru.fmt.university.dao.interfaces;

import ru.fmt.university.dao.GenericRepository;
import ru.fmt.university.model.entity.CourseEntity;

import java.util.List;

@org.springframework.stereotype.Repository
public interface CourseRepository extends GenericRepository<CourseEntity, Integer> {
    List<CourseEntity> findByGroups_id(Integer groupId);
}
