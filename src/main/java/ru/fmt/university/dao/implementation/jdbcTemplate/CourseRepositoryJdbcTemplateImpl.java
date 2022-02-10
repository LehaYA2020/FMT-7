package ru.fmt.university.dao.implementation.jdbcTemplate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.ICourseRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.dao.util.CourseMapper;
import ru.fmt.university.model.dto.Course;

import java.util.List;

@Repository
@Log4j2
@ConditionalOnProperty(name = "daoImpl", havingValue = "jdbc")
public class CourseRepositoryJdbcTemplateImpl implements ICourseRepository {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Course create(Course course) {
        log.trace("create({}).", course);
        try {
            jdbcTemplate.update(Query.INSERT_COURSE.getText(), course.getName(), course.getDescription());
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_COURSE, e);
        }
        log.debug("{} created", course);
        return course;
    }

    public List<Course> getAll() {
        log.trace("getAll().");
        List<Course> courses;
        try {
            courses = jdbcTemplate.query(Query.GET_ALL_COURSES.getText(), courseMapper);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_COURSES, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_COURSES, e);
        }
        log.trace("Found {} courses", courses.size());
        return courses;
    }

    public Course getById(Integer id) {
        log.trace("getById({}).", id);
        Course course;
        try {
            course = jdbcTemplate.queryForObject(Query.GET_COURSE_BY_ID.getText(), courseMapper, id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_COURSE_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_COURSE_BY_ID, e);
        }
        log.debug("Found {}.", course);
        return course;
    }

    public Course update(Course course) {
        log.trace("update({}).", course);
        try {
            jdbcTemplate.update(Query.UPDATE_COURSE.getText(), course.getName(), course.getDescription(), course.getId());
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_COURSE, e);
        }
        log.debug("Course updated {}", course);
        return course;
    }

    public boolean delete(Integer id) {
        log.trace("delete({}).", id);
        try {
            jdbcTemplate.update(Query.DELETE_COURSE.getText(), id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_COURSE, e);
        }
        log.debug("Course with id={} deleted.", id);
        return true;
    }

    public List<Course> getByGroupId(Integer groupId) {
        List<Course> courses;
        log.trace("getByGroupIa({}).", groupId);
        try {
            courses = jdbcTemplate.query(Query.GET_COURSES_BY_GROUP_ID.getText(), courseMapper, groupId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_COURSE, e);
        }
        log.debug("Found {}", courses);
        return courses;
    }
}
