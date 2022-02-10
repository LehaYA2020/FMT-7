package ru.fmt.university.dao.implementation.jdbcTemplate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.ILessonRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.dao.util.LessonMapper;
import ru.fmt.university.model.dto.Lesson;

import java.util.List;

@Repository
@Log4j2
@ConditionalOnProperty(name = "daoImpl", havingValue = "jdbc")
public class LessonRepositoryJdbcTemplateImpl implements ILessonRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LessonMapper lessonMapper;

    public Lesson create(Lesson lesson) {
        log.trace("create({}).", lesson);
        try {
            jdbcTemplate.update(Query.INSERT_LESSON.getText(), lesson.getCourseId(), lesson.getTeacherId(),
                    lesson.getClassRoom(), lesson.getDay().toString(), lesson.getStartTime(), lesson.getType().toString());
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_LESSON, e);
        }
        log.debug("Created {}.", lesson);

        return lesson;
    }

    public List<Lesson> getAll() {
        log.trace("getAll().");
        List<Lesson> lessons;
        try {
            lessons = jdbcTemplate.query(Query.GET_ALL_LESSONS.getText(), lessonMapper);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_LESSONS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_LESSONS, e);
        }
        log.debug("Found {}.", lessons);

        return lessons;
    }

    public Lesson getById(Integer id) {
        log.trace("getById({}).", id);
        Lesson lesson;
        try {
            lesson = jdbcTemplate.queryForObject(Query.GET_LESSON_BY_ID.getText(), lessonMapper, id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_ID, e);
        }
        log.debug("Found {}.", lesson);

        return lesson;
    }

    public boolean delete(Integer id) {
        log.trace("delete({}).", id);
        try {
            jdbcTemplate.update(Query.DELETE_LESSON.getText(), id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_LESSON_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_LESSON_BY_ID, e);
        }
        log.debug("Lesson with id={} deleted.", id);
        return true;
    }

    public Lesson update(Lesson lesson) {
        log.trace("delete({})", lesson);
        try {
            jdbcTemplate.update(Query.UPDATE_LESSON.getText(), lesson.getCourseId(), lesson.getTeacherId(),
                    lesson.getClassRoom(), lesson.getDay().toString(), lesson.getStartTime(),
                    lesson.getType().toString(), lesson.getId());
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_LESSON, e);
        }
        log.debug("Lesson {} updated.", lesson);

        return lesson;
    }

    public List<Lesson> getByStudent(Integer studentId) {
        log.trace("getByStudent({})", studentId);
        List<Lesson> lessons;
        try {
            lessons = jdbcTemplate.query(Query.GET_LESSON_BY_STUDENT.getText(), lessonMapper, studentId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_STUDENT, e);
        }
        log.debug("Found {} by student {}.", lessons, studentId);

        return lessons;
    }

    public List<Lesson> getByTeacher(Integer teacherId) {
        log.trace("getByTeacher({})", teacherId);
        List<Lesson> lessons;
        try {
            lessons = jdbcTemplate.query(Query.GET_LESSON_BY_TEACHER.getText(), lessonMapper, teacherId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_TEACHER, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_TEACHER, e);
        }
        log.debug("Found {} by teacher {}.", lessons, teacherId);

        return lessons;
    }

    public List<Lesson> getByGroup(Integer groupId) {
        log.trace("getByGroup({})", groupId);
        List<Lesson> lessons;
        try {
            lessons = jdbcTemplate.query(Query.GET_LESSON_BY_GROUP.getText(), lessonMapper, groupId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_GROUP, e);
        }
        log.debug("Found {} by group {}.", lessons, groupId);

        return lessons;
    }

    public List<Lesson> getByCourse(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<Lesson> lessons;
        try {
            lessons = jdbcTemplate.query(Query.GET_LESSON_BY_COURSE.getText(), lessonMapper, courseId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_COURSE, e);
        }
        log.debug("Found {} by course {}.", lessons, courseId);

        return lessons;
    }
}