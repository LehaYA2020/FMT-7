package ru.fmt.university.service;

import ru.fmt.university.model.dto.Course;

import java.util.Collection;

public interface CourseService extends GenericService<Course, Integer> {
    Collection<Course> getByGroupId(Integer id);
}
