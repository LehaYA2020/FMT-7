package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.dto.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class CourseRepositoryHibernateImplTest extends RepositoryTest {
    private static final Course FOR_CREATION = new Course("Course-" + 4, "forTest");

    @Test
    public void create() {
        courseRepositoryHibernate.create(FOR_CREATION);
        assertNotEquals(testCourseList, courseRepositoryHibernate.getAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, courseRepositoryHibernate.getById(4));
    }

    @Test
    public void getAll_shouldReturnAllCourses() {
        assertEquals(testCourseList, courseRepositoryHibernate.getAll());
    }

    @Test
    public void getById_shouldReturnCourseById() {
        Course expected = testCourseList.get(0);
        assertEquals(expected, courseRepositoryHibernate.getById(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> courseRepositoryHibernate.getById(10));

        assertEquals(MessagesConstants.CANNOT_GET_COURSE_BY_ID, exception.getMessage());
    }

    @Test
    public void update_shouldUpdateCourse() {
        Course expected = new Course(1, "Course-" + 1, "updated");
        courseRepositoryHibernate.update(expected);
        assertEquals(expected.getDescription(), courseRepositoryHibernate.getById(1).getDescription());
    }

    @Test
    public void delete_shouldDeleteCourse() {
        List<Course> expected = testCourseList.subList(0, 2);

        courseRepositoryHibernate.delete(3);

        assertEquals(expected, courseRepositoryHibernate.getAll());
    }

    @Test
    public void getByGroupId() {
        List<Course> expected = testCourseList.subList(0, 2);
        assertEquals(expected, courseRepositoryHibernate.getByGroupId(1));
    }
}
