package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.entity.CourseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class CourseRepositoryHibernateImplTest extends RepositoryTest {
    private static final CourseEntity FOR_CREATION = new CourseEntity("Course-" + 4, "forTest");

    @Test
    public void create() {
        courseRepositoryHibernate.save(FOR_CREATION);
        assertNotEquals(testCourseList, courseRepositoryHibernate.findAll(PageRequest.of(0,4, Sort.by("id"))).getContent());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, courseRepositoryHibernate.findById(4).get());
    }

    @Test
    public void getAll_shouldReturnAllCourses() {
        assertEquals(testCourseList.subList(1,2), courseRepositoryHibernate.findAll(PageRequest.of(1,1, Sort.by("id"))).getContent());
    }

    @Test
    public void getById_shouldReturnCourseById() {
        CourseEntity expected = testCourseList.get(0);
        assertEquals(expected, courseRepositoryHibernate.findById(1).get());
    }

    @Test
    public void getById_shouldThrowDaoException() {
        assertTrue(courseRepositoryHibernate.findById(10).isEmpty());
    }

    @Test
    public void update_shouldUpdateCourse() {
        CourseEntity expected = new CourseEntity(1, "Course-" + 1, "updated");
        courseRepositoryHibernate.save(expected);
        assertEquals(expected.getDescription(), courseRepositoryHibernate.findById(1).get().getDescription());
    }

    @Test
    public void delete_shouldDeleteCourse() {
        List<CourseEntity> expected = testCourseList.subList(0, 2);

        courseRepositoryHibernate.deleteById(3);

        assertEquals(expected, courseRepositoryHibernate.findAll(PageRequest.of(0,4, Sort.by("id"))).getContent());
    }

    @Test
    public void getByGroupId() {
        List<CourseEntity> expected = testCourseList.subList(0, 2);
        assertEquals(expected, courseRepositoryHibernate.findByGroups_id(1));
    }
}
