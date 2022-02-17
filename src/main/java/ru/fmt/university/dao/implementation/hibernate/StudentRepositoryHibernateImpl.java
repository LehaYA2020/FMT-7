package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.StudentRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.model.entity.GroupEntity;
import ru.fmt.university.model.entity.StudentEntity;

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
public class StudentRepositoryHibernateImpl implements StudentRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public StudentEntity save(StudentEntity student) {
        log.trace("create({})", student);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(student);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (RuntimeException e) {
            log.error(MessagesConstants.CANNOT_INSERT_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_STUDENT, e);
        } finally {
            entityManager.close();
        }
        log.debug("{} created", student);
        return student;
    }

    public List<StudentEntity> findAll() {
        log.trace("getAll()");
        List<StudentEntity> students;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            students = entityManager.createQuery("FROM StudentEntity", StudentEntity.class)
                    .getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_STUDENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_STUDENTS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}", students);
        return students;
    }

    public Optional<StudentEntity> findById(Integer id) {
        log.trace("getById({})", id);
        Optional<StudentEntity> student;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            student = Optional.of(entityManager.find(StudentEntity.class, id));
            if (student.isEmpty()) {
                throw new Exception("Student is null");
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_STUDENT_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_STUDENT_BY_ID, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}, {}, {}", student.get().getId(), student.get().getFirstName(), student.get().getLastName());
        return student;
    }

    public void deleteById(Integer id) {
        log.trace("delete({})", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            StudentEntity student = entityManager.find(StudentEntity.class, id);
            if (student != null) {
                entityManager.remove(student);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_STUDENT, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student with id={} deleted", id);
    }

    public void assignToGroup(Integer studentId, Integer groupId) {
        log.trace("assignToGroup({}, {})", studentId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.ASSIGN_STUDENT_TO_GROUP.getText())
                    .setParameter(1, groupId)
                    .setParameter(2, studentId)
                    .executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_TO_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_TO_GROUP, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student's {} assigned to group with id = {}", studentId, groupId);
    }

    public void updateGroupAssignment(Integer studentId, Integer groupId) {
        log.trace("updateGroupAssignments({}, {})", studentId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.UPDATE_STUDENT_ASSIGNMENTS.getText())
                    .setParameter(1, groupId)
                    .setParameter(2, studentId).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student's {} group assignments updated with {}", studentId, groupId);
    }

    public List<StudentEntity> findByGroup_Id(Integer groupId) {
        log.trace("getByGroupId({})", groupId);
        List<StudentEntity> students;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            students = entityManager.find(GroupEntity.class, groupId).getStudents();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
        }
        log.debug("Students {} found by {}", students, groupId);

        return students;
    }

    public void deleteFromGroup(Integer studentId, Integer groupId) {
        log.trace("deleteFromGroup({}, {})", studentId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.DELETE_STUDENT_FROM_GROUP.getText())
                    .setParameter(1, studentId)
                    .setParameter(2, groupId).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_STUDENT_FROM_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_STUDENT_FROM_GROUP, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student {} deleted from Group {})", studentId, groupId);
    }
}
