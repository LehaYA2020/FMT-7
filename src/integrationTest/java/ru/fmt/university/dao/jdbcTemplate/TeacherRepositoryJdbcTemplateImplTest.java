package ru.fmt.university.dao.jdbcTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.dto.Teacher;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=jdbc"})
public class TeacherRepositoryJdbcTemplateImplTest extends RepositoryTest {
    private static final Teacher FOR_CREATION = new Teacher(4, "T-4", "Teacher", testCourseList.get(1).getId());

    @Test
    public void create() {
        teacherRepository.create(FOR_CREATION);
        assertNotEquals(testTeacherList, teacherRepository.getAll());
        assertEquals(FOR_CREATION, teacherRepository.getById(FOR_CREATION.getId()));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepository.create(new Teacher(0, "", "", testCourseList.get(0).getId())));

        assertEquals(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, exception.getMessage());
    }

    @Test
    public void getAll() {
        assertEquals(testTeacherList, teacherRepository.getAll());
    }

    @Test
    public void getById() {
        assertEquals(testTeacherList.get(0), teacherRepository.getById(1));
    }

    @Test
    public void delete() {
        teacherRepository.delete(3);
        assertEquals(testTeacherList.subList(0, 2), teacherRepository.getAll());
    }

    @Test
    public void update() {
        Teacher teacher = new Teacher(2, "T-" + 2, "updated", testCourseList.get(1).getId());
        teacherRepository.update(teacher);
        assertEquals(teacher, teacherRepository.getById(2));
    }

    @Test
    public void getByCourse() {
        assertEquals(testTeacherList.subList(0, 2), teacherRepository.getByCourse(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testTeacherList.get(0), teacherRepository.getByLesson(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepository.getById(10));

        assertEquals("Can't get teacher by id: ", exception.getMessage());
    }

    @Test
    public void delete_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepository.delete(1));

        assertEquals(MessagesConstants.CANNOT_DELETE_TEACHER, exception.getMessage());
    }
}
