package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.entity.StudentEntity;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"daoImpl=hibernate"})
public class StudentRepositoryHibernateImplTest extends RepositoryTest {
    private static final StudentEntity FOR_CREATION = new StudentEntity("S-05", "Student", testGroupList.get(0));

    @Test
    public void create() {
        studentRepositoryHibernate.save(FOR_CREATION);
        assertNotEquals(testStudentList, studentRepositoryHibernate.findAll());
        FOR_CREATION.setId(5);
        assertEquals(FOR_CREATION, studentRepositoryHibernate.getById(5));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        assertEquals(4, studentRepositoryHibernate.findAll().size());
        Throwable exception = assertThrows(DaoException.class,
                () -> studentRepositoryHibernate.save(new StudentEntity(0, "", "234")));

        assertEquals(MessagesConstants.CANNOT_INSERT_STUDENT, exception.getMessage());
    }

    @Test
    public void getAll_shouldReturnAllStudentsFromDb() {
        assertEquals(testStudentList, studentRepositoryHibernate.findAll());
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
        studentRepositoryHibernate.deleteById(1);
        assertEquals(testStudentList.subList(1, 4), studentRepositoryHibernate.findAll());
    }

    @Test
    public void update_shouldUpdateStudent() {
        StudentEntity forUpdate = new StudentEntity(1, "S-01", "UPDATED", testGroupList.get(0));
        studentRepositoryHibernate.save(forUpdate);
        assertEquals(forUpdate, studentRepositoryHibernate.getById(1));
    }

    @Test
    public void assignToGroup_shouldAssignToGroup() {
        studentRepositoryHibernate.assignToGroup(testStudentList.get(3).getId(), testGroupList.get(2).getId());
        testStudentList.get(3).setGroup(testGroupList.get(2));
        assertEquals(testStudentList.subList(3, 4), studentRepositoryHibernate.findByGroup_Id(3));
        testStudentList.get(3).setGroup(null);
    }

    @Test
    public void updateGroupAssignment_shouldUpdateGroupAssignment() {
        studentRepositoryHibernate.updateGroupAssignment(testStudentList.get(1).getId(), testGroupList.get(1).getId());
        testStudentList.get(1).setGroup(testGroupList.get(1));
        assertEquals(testStudentList.subList(1, 2).get(0), studentRepositoryHibernate.findByGroup_Id(2).get(0));
        testStudentList.get(1).setGroup(testGroupList.get(0));
    }

    @Test
    public void getByGroup_shouldReturnStudentsByGroup() {
        assertEquals(testStudentList.subList(0, 2), studentRepositoryHibernate.findByGroup_Id(1));
    }

    @Test
    public void deleteFromGroup_shouldDeleteFromGroup() {
        studentRepositoryHibernate.deleteFromGroup(testStudentList.get(0).getId(), testGroupList.get(0).getId());
        assertEquals(testStudentList.subList(1, 2), studentRepositoryHibernate.findByGroup_Id(1));
    }
}
