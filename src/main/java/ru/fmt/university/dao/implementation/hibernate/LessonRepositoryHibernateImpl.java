package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.LessonRepository;
import ru.fmt.university.model.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "hibernate")
public class LessonRepositoryHibernateImpl implements LessonRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public LessonEntity save(LessonEntity lesson) {
        log.trace("create({}).", lesson);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(lesson);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_LESSON, e);
        } finally {
            entityManager.close();
        }
        log.debug("Created {}.", lesson);

        return lesson;
    }

    public Page<LessonEntity> findAll(Pageable pageable) {
        log.trace("getAll().");
        Page<LessonEntity> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lessons = new PageImpl<>(entityManager.createQuery("FROM LessonEntity", LessonEntity.class)
                    .getResultList(),pageable, pageable.getPageSize());
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_LESSONS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_LESSONS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}.", lessons);

        return lessons;
    }

    public Optional<LessonEntity> findById(Integer id) {
        log.trace("getById({}).", id);
        Optional<LessonEntity> lesson;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            lesson = Optional.of(entityManager.find(LessonEntity.class, id));
            entityManager.getTransaction().commit();
            entityManager.close();
            if (lesson == null) {
                throw new Exception("lesson is null");
            }
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_ID, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}.", lesson.get().getId());

        return lesson;
    }

    public void deleteById(Integer id) {
        log.trace("delete({}).", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("delete from lessons where id=?")
                    .setParameter(1, id).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_LESSON_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_LESSON_BY_ID, e);
        } finally {
            entityManager.close();
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
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_STUDENT, e);
        } finally {
            entityManager.close();
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
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_TEACHER, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_TEACHER, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by teacher {}.", lessons, teacherId);

        return lessons;
    }

    @org.springframework.transaction.annotation.Transactional
    public List<LessonEntity> findByGroups_id(Integer groupId) {
        log.trace("getByGroup({})", groupId);
        List<LessonEntity> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            lessons = entityManager.find(GroupEntity.class, groupId).getLessons();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_LESSON_BY_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_LESSON_BY_GROUP, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by group {}.", lessons, groupId);

        return lessons;
    }

    @Transactional
    public List<LessonEntity> findByCourse_id(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<LessonEntity> lessons;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            lessons = entityManager.find(CourseEntity.class, courseId).getLessons();
            entityManager.close();
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
