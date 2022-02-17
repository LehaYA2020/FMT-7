package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.entity.CourseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest(properties = {"daoImpl=jpa"})
public class CourseJpaTest extends RepositoryTest {
    private static final CourseEntity FOR_CREATION = new CourseEntity("Course-" + 4, "forTest");

    @Test
    public void create() {
        courseJpa.save(FOR_CREATION);
        assertNotEquals(testCourseList, courseJpa.findAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, courseJpa.findById(4).get());
    }

    @Test
    public void getAll_shouldReturnAllCourses() {
        assertEquals(testCourseList, courseJpa.findAll());
    }

    @Test
    public void getById_shouldReturnCourseById() {
        CourseEntity expected = testCourseList.get(1);
        assertEquals(expected, courseJpa.findById(2).get());
    }

    @Test
    public void update_shouldUpdateCourse() {
        CourseEntity expected = new CourseEntity(1, "Course-" + 1, "updated");
        courseJpa.save(expected);
        assertEquals(expected.getDescription(), courseJpa.findById(1).get().getDescription());
    }

    @Test
    public void delete_shouldDeleteCourse() {
        courseJpa.save(FOR_CREATION);
        assertNotEquals(testCourseList, courseJpa.findAll());

        courseJpa.deleteById(4);

        assertEquals(testCourseList, courseJpa.findAll());
    }

    @Test
    public void getByGroupId() {
        List<CourseEntity> expected = testCourseList.subList(0, 2);
        assertEquals(expected, courseJpa.findByGroups_id(1));
    }
}
