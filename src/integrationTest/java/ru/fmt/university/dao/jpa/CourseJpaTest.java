package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.entity.CourseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {"daoImpl=jpa"})
public class CourseJpaTest extends RepositoryTest {
    private static final CourseEntity FOR_CREATION = new CourseEntity("Course-" + 4, "forTest");

    @Test
    public void create() {
        courseJpa.saveAndFlush(FOR_CREATION);
        assertNotEquals(testCourseList, courseJpa.findAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, courseJpa.getById(4));
    }

    @Test
    public void getAll_shouldReturnAllCourses() {
        assertEquals(testCourseList, courseJpa.findAll());
    }

    @Test
    public void getById_shouldReturnCourseById() {
        CourseEntity expected = testCourseList.get(0);
        assertEquals(expected, courseJpa.findById(1).get());
    }

    @Test
    public void update_shouldUpdateCourse() {
        CourseEntity expected = new CourseEntity(1, "Course-" + 1, "updated");
        courseJpa.save(expected);
        assertEquals(expected.getDescription(), courseJpa.getById(1).getDescription());
    }

    @Test
    public void delete_shouldDeleteCourse() {
        List<CourseEntity> expected = testCourseList.subList(0, 2);

        courseJpa.deleteById(3);

        assertEquals(expected, courseJpa.findAll());
    }

    @Test
    public void getByGroupId() {
        List<CourseEntity> expected = testCourseList.subList(0, 2);
        assertEquals(expected, courseJpa.findByGroups_id(1));
    }
}
