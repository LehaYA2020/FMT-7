package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.dto.Teacher;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class TeacherRepositoryHibernateImplTest extends RepositoryTest {
    private static final Teacher FOR_CREATION = new Teacher("T-4", "Teacher", testCourseList.get(1).getId());

    @Test
    public void create() {
        teacherRepositoryHibernate.create(FOR_CREATION);
        assertNotEquals(testTeacherList, teacherRepositoryHibernate.getAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, teacherRepositoryHibernate.getById(FOR_CREATION.getId()));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepositoryHibernate.create(new Teacher(0, "", "", testCourseList.get(0).getId())));

        assertEquals(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, exception.getMessage());
    }

    @Test
    public void getAll() {
        assertEquals(testTeacherList, teacherRepositoryHibernate.getAll());
    }

    @Test
    public void getById() {
        assertEquals(testTeacherList.get(0), teacherRepositoryHibernate.getById(1));
    }

    @Test
    public void delete() {
        teacherRepositoryHibernate.delete(3);
        assertEquals(testTeacherList.subList(0, 2), teacherRepositoryHibernate.getAll());
    }

    @Test
    public void update() {
        Teacher teacher = new Teacher(2, "T-" + 2, "updated", testCourseList.get(1).getId());
        teacherRepositoryHibernate.update(teacher);
        assertEquals(teacher, teacherRepositoryHibernate.getById(2));
    }

    @Test
    public void getByCourse() {
        assertEquals(testTeacherList.subList(0, 2), teacherRepositoryHibernate.getByCourse(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testTeacherList.get(0), teacherRepositoryHibernate.getByLesson(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepositoryHibernate.getById(10));

        assertEquals("Can't get teacher by id: ", exception.getMessage());
    }

    @Test
    public void delete_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepositoryHibernate.delete(1));

        assertEquals(MessagesConstants.CANNOT_DELETE_TEACHER, exception.getMessage());
    }
}
