package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class StudentRepositoryHibernateImplTest extends RepositoryTest {
    private static final StudentEntity FOR_CREATION = new StudentEntity("S-05", "Student", testGroupList.get(0));

    @Test
    public void create() {
        studentRepositoryHibernate.save(FOR_CREATION);
        assertNotEquals(testStudentList, studentRepositoryHibernate.findAll(PageRequest.of(0, 5)).getContent());
        FOR_CREATION.setId(5);
        assertEquals(Optional.of(FOR_CREATION), studentRepositoryHibernate.findById(5));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        assertEquals(4, studentRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent().size());
        Throwable exception = assertThrows(DaoException.class,
                () -> studentRepositoryHibernate.save(new StudentEntity(0, "", "234")));

        assertEquals(MessagesConstants.CANNOT_INSERT_STUDENT, exception.getMessage());
    }

    @Test
    public void getAll_shouldReturnAllStudentsFromDb() {
        assertEquals(testStudentList, studentRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void getByID_shouldReturnStudentByIdFromDb() {
        assertEquals(Optional.of(testStudentList.get(0)), studentRepositoryHibernate.findById(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        assertTrue(studentRepositoryHibernate.findById(10).isEmpty());
    }

    @Test
    public void delete_shouldDeleteFromDb() {
        studentRepositoryHibernate.deleteById(1);
        assertEquals(testStudentList.subList(1, 4), studentRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void update_shouldUpdateStudent() {
        StudentEntity forUpdate = new StudentEntity(1, "S-01", "UPDATED", testGroupList.get(0));
        studentRepositoryHibernate.save(forUpdate);
        assertEquals(Optional.of(forUpdate), studentRepositoryHibernate.findById(1));
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
        studentRepositoryHibernate.updateGroupAssignment(2, 2);
        testStudentList.get(1).setGroup(testGroupList.get(1));
        assertEquals(Optional.of(testStudentList.get(1)), studentRepositoryHibernate.findById(2));
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
