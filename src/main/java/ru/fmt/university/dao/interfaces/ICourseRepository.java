package ru.fmt.university.dao.interfaces;

import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.dto.Course;

import java.util.List;

@Repository
public interface ICourseRepository extends IRepository<Course, Integer> {
    List<Course> getByGroupId(Integer groupId);
}
