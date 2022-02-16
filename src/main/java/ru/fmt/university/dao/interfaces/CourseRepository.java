package ru.fmt.university.dao.interfaces;

import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.dto.Course;
import ru.fmt.university.model.entity.CourseEntity;

import java.util.List;

@Repository
public interface CourseRepository extends IRepository<CourseEntity, Integer> {
    List<CourseEntity> findByGroups_id(Integer groupId);
}
