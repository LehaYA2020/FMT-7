package ru.fmt.university.service;

import ru.fmt.university.model.dto.Course;

import java.util.List;

public interface ICourseService extends IService<Course, Integer> {
    List<Course> getByGroupId(Integer id);
}
