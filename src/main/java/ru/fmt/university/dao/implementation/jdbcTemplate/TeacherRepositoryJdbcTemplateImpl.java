package ru.fmt.university.dao.implementation.jdbcTemplate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.ITeacherRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.dao.util.TeacherMapper;
import ru.fmt.university.model.dto.Teacher;

import java.util.List;

@Repository
@Log4j2
@ConditionalOnProperty(name = "daoImpl", havingValue = "jdbc")
public class TeacherRepositoryJdbcTemplateImpl implements ITeacherRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TeacherMapper teacherMapper;

    public Teacher create(Teacher teacher) {
        log.trace("create({})", teacher);
        try {
            jdbcTemplate.update(Query.INSERT_TEACHER.getText(), teacher.getFirstName(), teacher.getLastName(), teacher.getCourseId());
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, e);
        }
        log.debug("Teacher {} created.", teacher);
        return teacher;
    }

    public List<Teacher> getAll() {
        log.trace("getAll()");
        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(Query.GET_ALL_TEACHERS.getText(), teacherMapper);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_TEACHERS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_TEACHERS, e);
        }
        log.debug("{} found", teachers);
        return teachers;
    }

    public Teacher getById(Integer id) {
        log.trace("getById({})", id);
        Teacher teacher;
        try {
            teacher = jdbcTemplate.queryForObject(Query.GET_TEACHER_BY_ID.getText(), teacherMapper, id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHER_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHER_BY_ID, e);
        }
        log.debug("Teacher {} found", teacher);
        return teacher;
    }

    public Teacher update(Teacher teacher) {
        log.trace("update({})", teacher);
        try {
            jdbcTemplate.update(Query.UPDATE_TEACHER_BY_ID.getText(), teacher.getFirstName(), teacher.getLastName(),
                    teacher.getCourseId(), teacher.getId());
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_TEACHER_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_TEACHER_BY_ID, e);
        }
        log.debug("Teacher with id {} updated", teacher.getId());
        return teacher;
    }

    public boolean delete(Integer id) {
        log.trace("delete({})", id);
        try {
            jdbcTemplate.update(Query.DELETE_TEACHER.getText(), id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_TEACHER, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_TEACHER, e);
        }
        log.debug("Teacher with id {} deleted", id);
        return true;
    }

    public List<Teacher> getByCourse(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<Teacher> teachers;
        try {
            teachers = jdbcTemplate.query(Query.GET_TEACHERS_BY_COURSE.getText(), teacherMapper, courseId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHERS_BY_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHERS_BY_COURSE, e);
        }
        log.debug("Teachers {} found", teachers);
        return teachers;
    }

    public Teacher getByLesson(Integer lessonId) {
        log.trace("getByLesson({})", lessonId);
        Teacher teacher;
        try {
            teacher = jdbcTemplate.queryForObject(Query.GET_TEACHERS_BY_LESSON.getText(), teacherMapper, lessonId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHERS_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHERS_BY_LESSON, e);
        }
        log.debug("Teacher {} found", teacher);
        return teacher;
    }
}
