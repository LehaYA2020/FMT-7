package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fmt.university.dao.interfaces.ICourseRepository;
import ru.fmt.university.model.dto.Course;
import ru.fmt.university.service.ICourseService;

import java.util.List;

@Component
@Log4j2
public class CourseService implements ICourseService {
    @Autowired
    private ICourseRepository courseRepository;

    public void create(Course course) {
        log.debug("CourseService calls courseRepository.create({}).", course.getId());
        courseRepository.create(course);
    }

    public List<Course> getAll() {
        log.debug("CourseService calls courseRepository.getAll().");
        return courseRepository.getAll();
    }

    public Course getById(Integer id) {
        log.debug("CourseService calls courseRepository.getById({}).", id);
        return courseRepository.getById(id);
    }

    public Course update(Course forUpdate) {
        log.debug("CourseService calls courseRepository.update({}).", forUpdate.getId());
        return courseRepository.update(forUpdate);
    }

    public boolean delete(Integer id) {
        log.debug("CourseService calls courseRepository.delete({}).", id);
        return courseRepository.delete(id);
    }

    public List<Course> getByGroupId(Integer id) {
        log.debug("CourseService calls courseRepository.getByGroupId({}).", id);
        return courseRepository.getByGroupId(id);
    }
}
