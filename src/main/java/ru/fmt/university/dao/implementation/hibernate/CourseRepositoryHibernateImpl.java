package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.ICourseRepository;
import ru.fmt.university.model.entity.CourseEntity;
import ru.fmt.university.model.entity.GroupEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "hibernate")
public class CourseRepositoryHibernateImpl implements ICourseRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public CourseEntity saveAndFlush(CourseEntity course) {
        log.trace("create({}).", course);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(course);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_COURSE, e);
        } finally {
            entityManager.close();
        }
        log.debug("{} created", course);
        return course;
    }

    public List<CourseEntity> findAll() {
        log.trace("getAll().");
        List<CourseEntity> courses;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            courses = entityManager.createQuery("FROM CourseEntity", CourseEntity.class)
                    .getResultList();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_COURSES, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_COURSES, e);
        } finally {
            entityManager.close();
        }
        log.trace("Found {} courses", courses.size());
        return courses;
    }

    public CourseEntity getById(Integer id) {
        log.trace("getById({}).", id);
        CourseEntity course;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            course = entityManager.find(CourseEntity.class, id);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_COURSE_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_COURSE_BY_ID, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}.", course.getName());
        return course;
    }

    public CourseEntity save(CourseEntity course) {
        log.trace("update({}).", course);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(course);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_COURSE, e);
        } finally {
            entityManager.close();
        }
        log.debug("Course updated {}", course);
        return course;
    }

    public void deleteById(Integer id) {
        log.trace("delete({}).", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(CourseEntity.class, id));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_COURSE, e);
        } finally {
            entityManager.close();
        }
        log.debug("Course with id={} deleted.", id);
    }

    public List<CourseEntity> findByGroups_id(Integer groupId) {
        List<CourseEntity> courses;
        log.trace("getByGroupIa({}).", groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            courses = entityManager.find(GroupEntity.class, groupId).getCourses();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_COURSE, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}", courses);
        return courses;
    }
}
