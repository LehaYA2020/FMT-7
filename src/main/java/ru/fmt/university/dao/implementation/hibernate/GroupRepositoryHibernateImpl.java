package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.IGroupRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.dao.util.GroupMapper;
import ru.fmt.university.model.dto.Group;
import ru.fmt.university.model.entity.CourseEntity;
import ru.fmt.university.model.entity.GroupEntity;
import ru.fmt.university.model.entity.LessonEntity;
import ru.fmt.university.model.entity.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@Log4j2
@ConditionalOnProperty(name = "daoImpl", havingValue = "hibernate")
public class GroupRepositoryHibernateImpl implements IGroupRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @Autowired
    private GroupMapper groupMapper;

    public Group create(Group group) {
        log.trace("create({}).", group);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(groupMapper.toEntity(group));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            log.error(MessagesConstants.CANNOT_INSERT_GROUPS, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_GROUPS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Group {} created.", group);
        return group;
    }

    public List<Group> getAll() {
        log.trace("getAll()");
        List<Group> groups;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            groups = groupMapper.toGroup(entityManager.createQuery("FROM GroupEntity", GroupEntity.class).getResultList());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_GROUPS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_GROUPS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}.", groups);
        return groups;
    }

    public Group getById(Integer id) {
        log.trace("getById({})", id);
        Group group;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            group = groupMapper.toGroup(entityManager.find(GroupEntity.class, id));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_GROUP_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_GROUP_BY_ID, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}.", group);
        return group;
    }

    public boolean delete(Integer id) {
        log.trace("delete({})", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(GroupEntity.class, id));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP, e);
        } finally {
            entityManager.close();
        }
        log.debug("Group with id={}.", id);
        return true;
    }

    public boolean assignToCourse(Integer groupId, Integer courseId) {
        log.trace("assignToCourse({},{})", groupId, courseId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.ASSIGN_GROUP_TO_COURSE.getText())
                    .setParameter(1, groupId)
                    .setParameter(2, courseId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_GROUP_TO_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_GROUP_TO_COURSE, e);
        } finally {
            entityManager.close();
        }
        log.debug("Group {} assigned to course {}", groupId, courseId);
        return true;
    }

    public boolean deleteFromCourse(Integer groupId, Integer courseId) {
        log.trace("deleteFromCourse({}, {})", groupId, courseId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.DELETE_GROUP_FROM_COURSE.getText())
                    .setParameter(1, groupId)
                    .setParameter(2, courseId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP_FROM_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP_FROM_COURSE, e);
        } finally {
            entityManager.close();
        }
        log.debug("Course {} deleted from Course {}", groupId, courseId);
        return true;
    }

    public List<Group> getByLesson(int lessonId) {
        log.trace("getByLesson({})", lessonId);
        List<Group> groups;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            groups = groupMapper.toGroup(entityManager.find(LessonEntity.class, lessonId).getGroups());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_LESSON, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by {}", groups, lessonId);
        return groups;
    }

    public Group getByStudent(int studentId) {
        log.trace("getByStudent({})", studentId);
        Group group;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            group = groupMapper.toGroup(entityManager.find(StudentEntity.class, studentId).getGroup());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_STUDENT, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by {}", group, studentId);
        return group;
    }

    public List<Group> getByCourse(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<Group> groups;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            groups = groupMapper.toGroup(entityManager.find(CourseEntity.class, courseId).getGroups());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_LESSON, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {} by {}", groups, courseId);
        return groups;
    }

    public Group update(Group group) {
        log.trace("update({}).", group);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(groupMapper.toEntity(group));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_GROUP, e);
        } finally {
            entityManager.close();
        }
        log.debug("Group {} updated", group);
        return group;
    }

    public boolean assignToLesson(Integer lessonId, Integer groupId) {
        log.trace("assignToLesson({}, {})", lessonId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.ASSIGN_GROUP_TO_LESSON.getText())
                    .setParameter(1, lessonId)
                    .setParameter(2, groupId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_GROUPS_TO_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_GROUPS_TO_LESSON, e);
        } finally {
            entityManager.close();
        }
        log.debug("Groups {} assigned to lesson {})", groupId, lessonId);
        return true;
    }

    public boolean deleteFromLesson(Integer lessonId, Integer groupId) {
        log.trace("deleteFromLesson({}, {})", lessonId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.DELETE_GROUP_FROM_LESSON.getText())
                    .setParameter(1, lessonId)
                    .setParameter(2, groupId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP_FROM_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP_FROM_LESSON, e);
        } finally {
            entityManager.close();
        }
        log.debug("Group {} deleted from lesson {})", groupId, lessonId);
        return true;
    }
}
