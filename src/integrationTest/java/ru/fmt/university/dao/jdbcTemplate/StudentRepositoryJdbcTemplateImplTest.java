package ru.fmt.university.dao.jdbcTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.dto.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=jdbc"})
public class StudentRepositoryJdbcTemplateImplTest extends RepositoryTest {
    private static final Student FOR_CREATION = new Student(5, "S-05", "Student", 1);

    @Test
    public void create() {
        studentRepository.create(FOR_CREATION);
        assertNotEquals(testStudentList, studentRepository.getAll());
        assertEquals(FOR_CREATION, studentRepository.getById(FOR_CREATION.getId()));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        assertEquals(4, studentRepository.getAll().size());
        Throwable exception = assertThrows(DaoException.class,
                () -> studentRepository.create(new Student(0, "", "234")));

        assertEquals(MessagesConstants.CANNOT_INSERT_STUDENT, exception.getMessage());
    }

    @Test
    public void getAll_shouldReturnAllStudentsFromDb() {
        assertEquals(testStudentList, studentRepository.getAll());
    }

    @Test
    public void getByID_shouldReturnStudentByIdFromDb() {
        assertEquals(testStudentList.get(0), studentRepository.getById(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> studentRepository.getById(10));

        assertEquals(MessagesConstants.CANNOT_GET_STUDENT_BY_ID, exception.getMessage());
    }

    @Test
    public void delete_shouldDeleteFromDb() {
        studentRepository.delete(1);
        assertEquals(testStudentList.subList(1, 4), studentRepository.getAll());
    }

    @Test
    public void update_shouldUpdateStudent() {
        Student forUpdate = new Student(1, "S-01", "UPDATED", 1);
        studentRepository.update(forUpdate);
        assertEquals(forUpdate, studentRepository.getById(1));
    }

    @Test
    public void assignToGroup_shouldAssignToGroup() {
        studentRepository.assignToGroup(testStudentList.get(3).getId(), testGroupList.get(2).getId());
        testStudentList.get(3).setGroupId(3);
        assertEquals(testStudentList.subList(3, 4), studentRepository.getByGroupId(3));
        testStudentList.get(3).setGroupId(0);
    }

    @Test
    public void updateGroupAssignment_shouldUpdateGroupAssignment() {
        studentRepository.updateGroupAssignment(testStudentList.get(1).getId(), testGroupList.get(1).getId());
        testStudentList.get(1).setGroupId(2);
        assertEquals(testStudentList.subList(1, 2).get(0).getGroupId(), studentRepository.getByGroupId(2).get(0).getGroupId());
        testStudentList.get(1).setGroupId(1);
    }

    @Test
    public void getByGroup_shouldReturnStudentsByGroup() {
        assertEquals(testStudentList.subList(0, 2), studentRepository.getByGroupId(1));
    }

    @Test
    public void deleteFromGroup_shouldDeleteFromGroup() {
        studentRepository.deleteFromGroup(testStudentList.get(0).getId(), testGroupList.get(0).getId());
        assertEquals(testStudentList.subList(1, 2), studentRepository.getByGroupId(1));
    }
}
