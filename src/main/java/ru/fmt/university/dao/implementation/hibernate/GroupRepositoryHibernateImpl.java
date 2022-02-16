package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.GroupRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.model.entity.CourseEntity;
import ru.fmt.university.model.entity.GroupEntity;
import ru.fmt.university.model.entity.LessonEntity;
import ru.fmt.university.model.entity.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@Log4j2
@ConditionalOnProperty(name = "daoImpl", havingValue = "hibernate")
public class GroupRepositoryHibernateImpl implements GroupRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public GroupEntity save(GroupEntity group) {
        log.trace("create({}).", group);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(group);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (RuntimeException e) {
            log.error(MessagesConstants.CANNOT_INSERT_GROUPS, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_GROUPS, e);
        }
        log.debug("Group {} created.", group);
        return group;
    }

    public List<GroupEntity> findAll() {
        log.trace("getAll()");
        List<GroupEntity> groups;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            groups = entityManager.createQuery("FROM GroupEntity", GroupEntity.class).getResultList();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_GROUPS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_GROUPS, e);
        }
        log.debug("Found {}.", groups);
        return groups;
    }

    public Optional<GroupEntity> findById(Integer id) {
        log.trace("getById({})", id);
        Optional<GroupEntity> group;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            group = Optional.of(entityManager.find(GroupEntity.class, id));
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
            if (group == null) {
                throw new Exception("Student is null");
            }
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_GROUP_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_GROUP_BY_ID, e);
        }
        log.debug("Found {}.", group);
        return group;
    }

    public void deleteById(Integer id) {
        log.trace("delete({})", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(GroupEntity.class, id));
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP, e);
        }
        log.debug("Group with id={}.", id);
    }

    public void assignToCourse(Integer groupId, Integer courseId) {
        log.trace("assignToCourse({},{})", groupId, courseId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.ASSIGN_GROUP_TO_COURSE.getText())
                    .setParameter(1, groupId)
                    .setParameter(2, courseId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_GROUP_TO_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_GROUP_TO_COURSE, e);
        }
        log.debug("Group {} assigned to course {}", groupId, courseId);
    }

    public void deleteFromCourse(Integer groupId, Integer courseId) {
        log.trace("deleteFromCourse({}, {})", groupId, courseId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.DELETE_GROUP_FROM_COURSE.getText())
                    .setParameter(1, groupId)
                    .setParameter(2, courseId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP_FROM_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP_FROM_COURSE, e);
        }
        log.debug("Course {} deleted from Course {}", groupId, courseId);
    }

    public List<GroupEntity> findByLessons_Id(int lessonId) {
        log.trace("getByLesson({})", lessonId);
        List<GroupEntity> groups;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            groups = entityManager.find(LessonEntity.class, lessonId).getGroups();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_LESSON, e);
        }
        log.debug("Found {} by {}", groups, lessonId);
        return groups;
    }

    public GroupEntity findByStudents_Id(int studentId) {
        log.trace("getByStudent({})", studentId);
        GroupEntity group;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            group = entityManager.find(StudentEntity.class, studentId).getGroup();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_STUDENT, e);
        }
        log.debug("Found {} by {}", group, studentId);
        return group;
    }

    public List<GroupEntity> findByCourses_Id(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<GroupEntity> groups;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            groups = entityManager.find(CourseEntity.class, courseId).getGroups();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_LESSON, e);
        }
        log.debug("Found {} by {}", groups, courseId);
        return groups;
    }

    public void assignToLesson(Integer lessonId, Integer groupId) {
        log.trace("assignToLesson({}, {})", lessonId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.ASSIGN_GROUP_TO_LESSON.getText())
                    .setParameter(1, lessonId)
                    .setParameter(2, groupId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_GROUPS_TO_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_GROUPS_TO_LESSON, e);
        }
        log.debug("Groups {} assigned to lesson {})", groupId, lessonId);
    }

    public void deleteFromLesson(Integer lessonId, Integer groupId) {
        log.trace("deleteFromLesson({}, {})", lessonId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.DELETE_GROUP_FROM_LESSON.getText())
                    .setParameter(1, lessonId)
                    .setParameter(2, groupId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP_FROM_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP_FROM_LESSON, e);
        }
        log.debug("Group {} deleted from lesson {})", groupId, lessonId);
    }
}
