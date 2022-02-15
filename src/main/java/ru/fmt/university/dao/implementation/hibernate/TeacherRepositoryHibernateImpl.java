package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.ITeacherRepository;
import ru.fmt.university.model.entity.CourseEntity;
import ru.fmt.university.model.entity.LessonEntity;
import ru.fmt.university.model.entity.TeacherEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "hibernate")
public class TeacherRepositoryHibernateImpl implements ITeacherRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public TeacherEntity save(TeacherEntity teacher) {
        log.trace("create({})", teacher);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(teacher);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (RuntimeException e) {
            log.error(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, e);
        }
        log.debug("Teacher {} created.", teacher);
        return teacher;
    }

    public List<TeacherEntity> findAll() {
        log.trace("getAll()");
        List<TeacherEntity> teachers;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            teachers = entityManager.createQuery("FROM TeacherEntity", TeacherEntity.class)
                    .getResultList();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_TEACHERS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_TEACHERS, e);
        }
        log.debug("{} found", teachers);
        return teachers;
    }

    public TeacherEntity getById(Integer id) {
        log.trace("getById({})", id);
        TeacherEntity entity;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity = entityManager.find(TeacherEntity.class, id);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
            if (entity == null) {
                throw new Exception("Student is null");
            }
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHER_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHER_BY_ID, e);
        }
        log.debug("Teacher {}, {}, {} found", entity.getFirstName(), entity.getLastName(), entity.getCourse().getName());
        return entity;
    }

    public void deleteById(Integer id) {
        log.trace("delete({})", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(TeacherEntity.class, id));
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_TEACHER, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_TEACHER, e);
        }
        log.debug("Teacher with id {} deleted", id);
    }

    public List<TeacherEntity> findByCourse_id(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<TeacherEntity> teachers;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            teachers = entityManager.find(CourseEntity.class, courseId).getTeachers();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHERS_BY_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHERS_BY_COURSE, e);
        }
        log.debug("Teachers {} found", teachers);
        return teachers;
    }

    public TeacherEntity findByLessons_id(Integer lessonId) {
        log.trace("getByLesson({})", lessonId);
        TeacherEntity teacher;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            teacher = entityManager.find(LessonEntity.class, lessonId).getTeacher();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHERS_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHERS_BY_LESSON, e);
        }
        log.debug("Teacher {} found", teacher);
        return teacher;
    }
}
