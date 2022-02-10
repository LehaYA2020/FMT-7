package ru.fmt.university.dao.implementation.jdbcTemplate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.IStudentRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.dao.util.StudentMapper;
import ru.fmt.university.model.dto.Student;

import java.util.List;

@Repository
@Log4j2
@ConditionalOnProperty(name = "daoImpl", havingValue = "jdbc")
public class StudentRepositoryJdbcTemplateImpl implements IStudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StudentMapper studentMapper;

    public Student create(Student student) {
        log.trace("create({})", student);
        try {
            jdbcTemplate.update(Query.INSERT_STUDENT.getText(), student.getFirstName(), student.getLastName(), student.getGroupId());
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_STUDENT, e);
        }
        log.debug("{} created", student);
        return student;
    }

    public List<Student> getAll() {
        log.trace("getAll()");
        List<Student> students;
        try {
            students = jdbcTemplate.query(Query.GET_ALL_STUDENTS.getText(), studentMapper);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_ALL_STUDENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_ALL_STUDENTS, e);
        }
        log.debug("Found {}", students);
        return students;
    }

    public Student getById(Integer id) {
        log.trace("getById({})", id);
        Student student;
        try {
            student = jdbcTemplate.queryForObject(Query.GET_STUDENT_BY_ID.getText(), studentMapper, id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_STUDENT_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_STUDENT_BY_ID, e);
        }
        log.debug("Found {}", student);
        return student;
    }

    public Student update(Student student) {
        log.trace("update({})", student);
        try {
            jdbcTemplate.update(Query.UPDATE_STUDENT.getText(), student.getFirstName(), student.getLastName(), student.getId());
            if (student.getGroupId() != 0) {
                updateGroupAssignment(student.getId(), student.getGroupId());
            }
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_STUDENT, e);
        }
        log.debug("Student with id={} updated", student.getId());

        return student;
    }

    public boolean delete(Integer id) {
        log.trace("delete({})", id);
        try {
            jdbcTemplate.update(Query.DELETE_STUDENT.getText(), id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_STUDENT, e);
        }
        log.debug("Student with id={} deleted", id);
        return true;
    }

    public boolean assignToGroup(Integer studentId, Integer groupId) {
        log.trace("assignToGroup({}, {})", studentId, groupId);
        try {
            jdbcTemplate.update(Query.ASSIGN_STUDENT_TO_GROUP.getText(), groupId, studentId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_TO_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_TO_GROUP, e);
        }
        log.debug("Student's {} assigned to group with id = {}", studentId, groupId);
        return true;
    }

    public boolean updateGroupAssignment(Integer studentId, Integer groupId) {
        log.trace("updateGroupAssignments({}, {})", studentId, groupId);
        try {
            jdbcTemplate.update(Query.UPDATE_STUDENT_ASSIGNMENTS.getText(), groupId, studentId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
        }
        log.debug("Student's {} group assignments updated with {}", studentId, groupId);
        return true;
    }

    public List<Student> getByGroupId(Integer groupId) {
        log.trace("getByGroupId({})", groupId);
        List<Student> students;
        try {
            students = jdbcTemplate.query(Query.GET_STUDENT_BY_GROUP.getText(), studentMapper, groupId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_STUDENT_ASSIGNMENTS, e);
        }
        log.debug("Students {} found by {}", students, groupId);

        return students;
    }

    public boolean deleteFromGroup(Integer studentId, Integer groupId) {
        log.trace("deleteFromGroup({}, {})", studentId, groupId);
        try {
            jdbcTemplate.update(Query.DELETE_STUDENT_FROM_GROUP.getText(), studentId, groupId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_STUDENT_FROM_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_STUDENT_FROM_GROUP, e);
        }
        log.debug("Student {} deleted from Group {})", studentId, groupId);
        return true;
    }
}
