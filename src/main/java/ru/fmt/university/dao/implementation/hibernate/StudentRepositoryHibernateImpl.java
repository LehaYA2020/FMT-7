package ru.fmt.university.dao.implementation.hibernate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.IStudentRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.dao.util.StudentMapper;
import ru.fmt.university.model.dto.Student;
import ru.fmt.university.model.entity.GroupEntity;
import ru.fmt.university.model.entity.StudentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "hibernate")
public class StudentRepositoryHibernateImpl implements IStudentRepository {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    @Autowired
    private StudentMapper studentMapper;

    public Student create(Student student) {
        log.trace("create({})", student);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(studentMapper.toEntity(student));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            log.error(MessagesConstants.CANNOT_INSERT_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_STUDENT, e);
        } finally {
            entityManager.close();
        }
        log.debug("{} created", student);
        return student;
    }

    public List<Student> getAll() {
        log.trace("getAll()");
        List<Student> students;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            students = studentMapper.toStudent(entityManager.createQuery("FROM StudentEntity", StudentEntity.class)
                    .getResultList());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_STUDENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_STUDENTS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}", students);
        return students;
    }

    public Student getById(Integer id) {
        log.trace("getById({})", id);
        StudentEntity student;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            student = entityManager.find(StudentEntity.class, id);
            if (student == null) {
                log.error(MessagesConstants.CANNOT_GET_STUDENT_BY_ID);
                throw new DaoException(MessagesConstants.CANNOT_GET_STUDENT_BY_ID);
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_STUDENT_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_STUDENT_BY_ID, e);
        } finally {
            entityManager.close();
        }
        log.debug("Found {}", student);
        return studentMapper.toStudent(student);
    }

    public Student update(Student student) {
        log.trace("update({})", student);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(studentMapper.toEntity(student));
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_STUDENT, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student with id={} updated", student.getId());

        return student;
    }

    public boolean delete(Integer id) {
        log.trace("delete({})", id);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            StudentEntity student = entityManager.find(StudentEntity.class, id);
            if (student != null) {
                entityManager.remove(student);
            }
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_STUDENT, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student with id={} deleted", id);
        return true;
    }

    public boolean assignToGroup(Integer studentId, Integer groupId) {
        log.trace("assignToGroup({}, {})", studentId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.ASSIGN_STUDENT_TO_GROUP.getText())
                    .setParameter(1, groupId)
                    .setParameter(2, studentId)
                    .executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_TO_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_TO_GROUP, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student's {} assigned to group with id = {}", studentId, groupId);
        return true;
    }

    public boolean updateGroupAssignment(Integer studentId, Integer groupId) {
        log.trace("updateGroupAssignments({}, {})", studentId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.UPDATE_STUDENT_ASSIGNMENTS.getText())
                    .setParameter(1, groupId)
                    .setParameter(2, studentId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();} catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student's {} group assignments updated with {}", studentId, groupId);
        return true;
    }

    public List<Student> getByGroupId(Integer groupId) {
        log.trace("getByGroupId({})", groupId);
        List<Student> students;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            students = studentMapper.toStudent(entityManager.find(GroupEntity.class, groupId).getStudents());
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
        } finally {
            entityManager.close();
        }
        log.debug("Students {} found by {}", students, groupId);

        return students;
    }

    public boolean deleteFromGroup(Integer studentId, Integer groupId) {
        log.trace("deleteFromGroup({}, {})", studentId, groupId);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.createNativeQuery(Query.DELETE_STUDENT_FROM_GROUP.getText())
                    .setParameter(1, studentId)
                    .setParameter(2, groupId).executeUpdate();
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_STUDENT_FROM_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_STUDENT_FROM_GROUP, e);
        } finally {
            entityManager.close();
        }
        log.debug("Student {} deleted from Group {})", studentId, groupId);
        return true;
    }
}
