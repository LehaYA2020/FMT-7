package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.ILessonRepository;
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

    public LessonEntity save(LessonEntity lesson) {
        log.trace("create({}).", lesson);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(lesson);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_LESSON, e);
        }
        log.debug("Created {}.", lesson);

        return lesson;
    }

    public List<LessonEntity> findAll() {
        log.trace("getAll().");
        List<LessonEntity> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = entityManager.createQuery("FROM LessonEntity", LessonEntity.class)
                    .getResultList();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_LESSONS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_LESSONS, e);
        }
        log.debug("Found {}.", lessons);

        return lessons;
    }

    public LessonEntity getById(Integer id) {
        log.trace("getById({}).", id);
        LessonEntity lesson;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lesson = entityManager.find(LessonEntity.class, id);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
            if (lesson==null) {
                throw new Exception("lesson is null");
            }
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_ID, e);
        }
        log.debug("Found {}.", lesson);

        return lesson;
    }

    public void deleteById(Integer id) {
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
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_LESSON_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_LESSON_BY_ID, e);
        }
        log.debug("Lesson with id={} deleted.", id);
    }

    public List<LessonEntity> findByStudents_id(Integer studentId) {
        log.trace("getByStudent({})", studentId);
        List<LessonEntity> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = entityManager.find(StudentEntity.class, studentId).getGroup().getLessons();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_STUDENT, e);
        }
        log.debug("Found {} by student {}.", lessons, studentId);

        return lessons;
    }

    public List<LessonEntity> findByTeacher_id(Integer teacherId) {
        log.trace("getByTeacher({})", teacherId);
        List<LessonEntity> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = entityManager.find(TeacherEntity.class, teacherId).getLessons();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_TEACHER, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_TEACHER, e);
        }
        log.debug("Found {} by teacher {}.", lessons, teacherId);

        return lessons;
    }

    public List<LessonEntity> findByGroups_id(Integer groupId) {
        log.trace("getByGroup({})", groupId);
        List<LessonEntity> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = entityManager.find(GroupEntity.class, groupId).getLessons();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_GROUP, e);
        }
        log.debug("Found {} by group {}.", lessons, groupId);

        return lessons;
    }

    public List<LessonEntity> findByCourse_id(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<LessonEntity> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = entityManager.find(CourseEntity.class, courseId).getLessons();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_COURSE, e);
        }
        log.debug("Found {} by course {}.", lessons, courseId);

        return lessons;
    }
}
