package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.entity.TeacherEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class TeacherRepositoryHibernateImplTest extends RepositoryTest {
    private static final TeacherEntity FOR_CREATION = new TeacherEntity("T-4", "Teacher", testCourseList.get(1));

    @Test
    public void create() {
        teacherRepositoryHibernate.save(FOR_CREATION);
        assertNotEquals(testTeacherList, teacherRepositoryHibernate.findAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, teacherRepositoryHibernate.findById(FOR_CREATION.getId()).get());
    }

    @Test
    public void create_shouldThrow_DaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepositoryHibernate.save(new TeacherEntity(0, "", "", testCourseList.get(0))));

        assertEquals(MessagesConstants.CANNOT_INSERT_TEACHERS_LIST, exception.getMessage());
    }

    @Test
    public void getAll() {
        assertEquals(testTeacherList, teacherRepositoryHibernate.findAll());
    }

    @Test
    public void getById() {
        assertEquals(testTeacherList.get(0), teacherRepositoryHibernate.findById(1).get());
    }

    @Test
    public void delete() {
        teacherRepositoryHibernate.deleteById(3);
        assertEquals(testTeacherList.subList(0, 2), teacherRepositoryHibernate.findAll());
    }

    @Test
    public void update() {
        TeacherEntity teacher = new TeacherEntity(2, "T-" + 2, "updated", testCourseList.get(1));
        teacherRepositoryHibernate.save(teacher);
        assertEquals(teacher, teacherRepositoryHibernate.findById(2).get());
    }

    @Test
    public void getByCourse() {
        assertEquals(testTeacherList.subList(0, 2), teacherRepositoryHibernate.findByCourse_id(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testTeacherList.get(0), teacherRepositoryHibernate.findByLessons_id(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepositoryHibernate.findById(10));

        assertEquals("Can't get teacher by id: ", exception.getMessage());
    }

    @Test
    public void delete_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> teacherRepositoryHibernate.deleteById(1));

        assertEquals(MessagesConstants.CANNOT_DELETE_TEACHER, exception.getMessage());
    }
}
