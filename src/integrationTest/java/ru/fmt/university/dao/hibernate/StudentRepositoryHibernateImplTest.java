package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.dto.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class StudentRepositoryHibernateImplTest extends RepositoryTest {
    private static final Student FOR_CREATION = new Student("S-05", "Student", 1);

    @Test
    public void create() {
        studentRepositoryHibernate.create(FOR_CREATION);
        assertNotEquals(testStudentList, studentRepositoryHibernate.getAll());
        FOR_CREATION.setId(5);
        assertEquals(FOR_CREATION, studentRepositoryHibernate.getById(5));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        assertEquals(4, studentRepositoryHibernate.getAll().size());
        Throwable exception = assertThrows(DaoException.class,
                () -> studentRepositoryHibernate.create(new Student(0, "", "234")));

        assertEquals(MessagesConstants.CANNOT_INSERT_STUDENT, exception.getMessage());
    }

    @Test
    public void getAll_shouldReturnAllStudentsFromDb() {
        assertEquals(testStudentList, studentRepositoryHibernate.getAll());
    }

    @Test
    public void getByID_shouldReturnStudentByIdFromDb() {
        assertEquals(testStudentList.get(0), studentRepositoryHibernate.getById(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> studentRepositoryHibernate.getById(10));

        assertEquals(MessagesConstants.CANNOT_GET_STUDENT_BY_ID, exception.getMessage());
    }

    @Test
    public void delete_shouldDeleteFromDb() {
        studentRepositoryHibernate.delete(1);
        assertEquals(testStudentList.subList(1, 4), studentRepositoryHibernate.getAll());
    }

    @Test
    public void update_shouldUpdateStudent() {
        Student forUpdate = new Student(1, "S-01", "UPDATED", 1);
        studentRepositoryHibernate.update(forUpdate);
        assertEquals(forUpdate, studentRepositoryHibernate.getById(1));
    }

    @Test
    public void assignToGroup_shouldAssignToGroup() {
        studentRepositoryHibernate.assignToGroup(testStudentList.get(3).getId(), testGroupList.get(2).getId());
        testStudentList.get(3).setGroupId(3);
        assertEquals(testStudentList.subList(3, 4), studentRepositoryHibernate.getByGroupId(3));
        testStudentList.get(3).setGroupId(0);
    }

    @Test
    public void updateGroupAssignment_shouldUpdateGroupAssignment() {
        studentRepositoryHibernate.updateGroupAssignment(testStudentList.get(1).getId(), testGroupList.get(1).getId());
        testStudentList.get(1).setGroupId(2);
        assertEquals(testStudentList.subList(1, 2).get(0).getGroupId(), studentRepositoryHibernate.getByGroupId(2).get(0).getGroupId());
        testStudentList.get(1).setGroupId(1);
    }

    @Test
    public void getByGroup_shouldReturnStudentsByGroup() {
        assertEquals(testStudentList.subList(0, 2), studentRepositoryHibernate.getByGroupId(1));
    }

    @Test
    public void deleteFromGroup_shouldDeleteFromGroup() {
        studentRepositoryHibernate.deleteFromGroup(testStudentList.get(0).getId(), testGroupList.get(0).getId());
        assertEquals(testStudentList.subList(1, 2), studentRepositoryHibernate.getByGroupId(1));
    }
}
