package ru.fmt.university.dao.jdbcTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.dto.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=jdbc"})
public class CourseRepositoryJdbcTemplateImplTest extends RepositoryTest {
    private static final Course FOR_CREATION = new Course(4, "Course-" + 4, "forTest");

    @Test
    public void create() {
        courseRepository.create(FOR_CREATION);
        assertNotEquals(testCourseList, courseRepository.getAll());
        assertEquals(FOR_CREATION, courseRepository.getById(FOR_CREATION.getId()));
    }

    @Test
    public void getAll_shouldReturnAllCourses() {
        assertEquals(testCourseList, courseRepository.getAll());
    }

    @Test
    public void getById_shouldReturnCourseById() {
        Course expected = testCourseList.get(0);
        assertEquals(expected, courseRepository.getById(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> courseRepository.getById(10));

        assertEquals(MessagesConstants.CANNOT_GET_COURSE_BY_ID, exception.getMessage());
    }

    @Test
    public void update_shouldUpdateCourse() {
        Course expected = new Course(1, "Course-" + 1, "updated");
        courseRepository.update(new Course(1, "Course-1", "updated"));
        assertEquals(expected, courseRepository.getById(1));
    }

    @Test
    public void delete_shouldDeleteCourse() {
        List<Course> expected = testCourseList.subList(0, 2);

        courseRepository.delete(3);

        assertEquals(expected, courseRepository.getAll());
    }

    @Test
    public void getByGroupId() {
        List<Course> expected = testCourseList.subList(0, 2);
        assertEquals(expected, courseRepository.getByGroupId(1));
    }
}
