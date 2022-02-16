package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fmt.university.dao.interfaces.CourseRepository;
import ru.fmt.university.model.dto.Course;
import ru.fmt.university.service.ICourseService;
import ru.fmt.university.service.util.CourseMapper;

import java.util.List;

@Component
@Log4j2
public class CourseService implements ICourseService {
    @Autowired(required = false)
    private CourseRepository courseRepository;
    @Autowired
    private CourseMapper courseMapper;

    public void create(Course course) {
        log.debug("CourseService calls courseRepository.create({}).", course.getId());
        courseRepository.save(courseMapper.toEntity(course));
    }

    public List<Course> getAll() {
        log.debug("CourseService calls courseRepository.getAll().");
        return courseMapper.toCourse(courseRepository.findAll());
    }

    public Course getById(Integer id) {
        log.debug("CourseService calls courseRepository.getById({}).", id);
        return courseMapper.toCourse(courseRepository.findById(id).get());
    }

    public Course update(Course forUpdate) {
        log.debug("CourseService calls courseRepository.update({}).", forUpdate.getId());
        return courseMapper.toCourse(courseRepository.save(courseMapper.toEntity(forUpdate)));
    }

    public boolean deleteById(Integer id) {
        log.debug("CourseService calls courseRepository.delete({}).", id);
        courseRepository.deleteById(id);
        return true;
    }

    public List<Course> getByGroupId(Integer id) {
        log.debug("CourseService calls courseRepository.getByGroupId({}).", id);
        return courseMapper.toCourse(courseRepository.findByGroups_id(id));
    }
}
