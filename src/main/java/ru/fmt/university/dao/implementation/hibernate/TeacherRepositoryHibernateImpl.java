package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.TeacherRepository;
import ru.fmt.university.model.entity.CourseEntity;
import ru.fmt.university.model.entity.LessonEntity;
import ru.fmt.university.model.entity.TeacherEntity;

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
public class TeacherRepositoryHibernateImpl implements TeacherRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public TeacherEntity save(TeacherEntity teacher) {
        log.trace("create({})", teacher);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(teacher);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (RuntimeException e) {
            log.error(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, e);
        } finally {
            entityManager.close();
        }
        log.debug("Teacher {} created.", teacher);
        return teacher;
    }

    public Page<TeacherEntity> findAll(Pageable pageable) {
        log.trace("getAll()");
        Page<TeacherEntity> teachers;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            teachers = new PageImpl<>(entityManager.createQuery("FROM TeacherEntity", TeacherEntity.class)
                    .getResultList(),pageable, pageable.getPageSize());
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_TEACHERS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_TEACHERS, e);
        } finally {
            entityManager.close();
        }
        log.debug("{} found", teachers);
        return teachers;
    }

    public Optional<TeacherEntity> findById(Integer id) {
        log.trace("getById({})", id);
        Optional<TeacherEntity> entity;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entity = Optional.of(entityManager.find(TeacherEntity.class, id));
            entityManager.getTransaction().commit();
            entityManager.close();
            if (entity == null) {
                throw new Exception("Student is null");
            }
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHER_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHER_BY_ID, e);
        } finally {
            entityManager.close();
        }
        log.debug("Teacher {}, {}, {} found", entity.get().getFirstName(), entity.get().getLastName()
                , entity.get().getCourse().getName());
        return entity;
    }

    public void deleteById(Integer id) {
        log.trace("delete({})", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(TeacherEntity.class, id));
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_TEACHER, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_TEACHER, e);
        } finally {
            entityManager.close();
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
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHERS_BY_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHERS_BY_COURSE, e);
        } finally {
            entityManager.close();
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
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_TEACHERS_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_TEACHERS_BY_LESSON, e);
        } finally {
            entityManager.close();
        }
        log.debug("Teacher {} found", teacher);
        return teacher;
    }
}
