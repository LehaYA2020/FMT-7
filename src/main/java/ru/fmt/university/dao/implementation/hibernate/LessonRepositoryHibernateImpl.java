package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.ILessonRepository;
import ru.fmt.university.dao.util.LessonMapper;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Log4j2
@Repository
@ConditionalOnProperty(name = "daoImpl", havingValue = "hibernate")
public class LessonRepositoryHibernateImpl implements ILessonRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @Autowired
    private LessonMapper lessonMapper;

    public Lesson create(Lesson lesson) {
        log.trace("create({}).", lesson);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(lessonMapper.toEntity(lesson));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_LESSON, e);
        } finally {
            entityManager.close();
        }
        log.debug("Created {}.", lesson);

        return lesson;
    }

    public List<Lesson> getAll() {
        log.trace("getAll().");
        List<Lesson> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = lessonMapper.toLesson(entityManager.createQuery("FROM LessonEntity", LessonEntity.class)
                    .getResultList());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_LESSONS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_LESSONS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}.", lessons);

        return lessons;
    }

    public Lesson getById(Integer id) {
        log.trace("getById({}).", id);
        Lesson lesson;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lesson = lessonMapper.toLesson(entityManager.find(LessonEntity.class, id));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_ID, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}.", lesson);

        return lesson;
    }

    public boolean delete(Integer id) {
        log.trace("delete({}).", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("update lessons set teacher_id=null where id=?")
                    .setParameter(1, id).executeUpdate();
            entityManager.createNativeQuery("delete from lessons where id=?")
                    .setParameter(1, id).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_LESSON_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_LESSON_BY_ID, e);
        } finally {
            entityManager.close();
        }
        log.debug("Lesson with id={} deleted.", id);
        return true;
    }

    public Lesson update(Lesson lesson) {
        log.trace("delete({})", lesson);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(lessonMapper.toEntity(lesson));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_LESSON, e);
        } finally {
            entityManager.close();
        }
        log.debug("Lesson {} updated.", lesson);

        return lesson;
    }

    public List<Lesson> getByStudent(Integer studentId) {
        log.trace("getByStudent({})", studentId);
        List<Lesson> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = lessonMapper.toLesson(entityManager.find(StudentEntity.class, studentId).getGroup().getLessons());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_STUDENT, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by student {}.", lessons, studentId);

        return lessons;
    }

    public List<Lesson> getByTeacher(Integer teacherId) {
        log.trace("getByTeacher({})", teacherId);
        List<Lesson> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = lessonMapper.toLesson(entityManager.find(TeacherEntity.class, teacherId).getLessons());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_TEACHER, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_TEACHER, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by teacher {}.", lessons, teacherId);

        return lessons;
    }

    public List<Lesson> getByGroup(Integer groupId) {
        log.trace("getByGroup({})", groupId);
        List<Lesson> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = lessonMapper.toLesson(entityManager.find(GroupEntity.class, groupId).getLessons());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_GROUP, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by group {}.", lessons, groupId);

        return lessons;
    }

    public List<Lesson> getByCourse(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<Lesson> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = lessonMapper.toLesson(entityManager.find(CourseEntity.class, courseId).getLessons());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_COURSE, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by course {}.", lessons, courseId);

        return lessons;
    }
}
