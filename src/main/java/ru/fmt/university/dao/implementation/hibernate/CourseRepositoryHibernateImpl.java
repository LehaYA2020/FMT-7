package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.CourseRepository;
import ru.fmt.university.model.entity.CourseEntity;
import ru.fmt.university.model.entity.GroupEntity;

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
public class CourseRepositoryHibernateImpl implements CourseRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public CourseEntity save(CourseEntity course) {
        log.trace("create({}).", course);
        CourseEntity created;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            created = entityManager.merge(course);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_COURSE, e);
        } finally {
            entityManager.close();
        }
        log.debug("{} created", course);
        return created;
    }

    public Page<CourseEntity> findAll(Pageable pageable) {
        log.trace("getAll().");
        Page<CourseEntity> courses;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            long total = entityManager.createQuery("SELECT COUNT(ce) from CourseEntity ce", Long.class).getSingleResult();
            courses = new PageImpl<>(entityManager.createQuery("FROM CourseEntity", CourseEntity.class)
                    .setFirstResult((pageable.getPageNumber()) * (pageable.getPageSize() - (pageable.getPageSize() - 1)))
                    .setMaxResults(pageable.getPageSize())
                    .getResultList(), pageable, total);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_COURSES, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_COURSES, e);
        } finally {
            entityManager.close();
        }
        log.trace("Found {} courses", courses.getSize());
        return courses;
    }

    public Optional<CourseEntity> findById(Integer id) {
        log.trace("getById({}).", id);
        Optional<CourseEntity> course;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            course = Optional.ofNullable(entityManager.find(CourseEntity.class, id));
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_COURSE_BY_ID, e);
            entityManager.getTransaction().rollback();
            throw new DaoException(MessagesConstants.CANNOT_GET_COURSE_BY_ID, e);
        } finally {
            entityManager.close();
        }
        return course;
    }

    public void deleteById(Integer id) {
        log.trace("delete({}).", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(CourseEntity.class, id));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_COURSE, e);
            entityManager.getTransaction().rollback();
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
            courses = entityManager.find(GroupEntity.class, groupId).getCourses();
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
