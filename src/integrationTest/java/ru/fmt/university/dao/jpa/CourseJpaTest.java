package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.entity.CourseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest(properties = {"daoImpl=jpa"})
public class CourseJpaTest extends RepositoryTest {
    private static final CourseEntity FOR_CREATION = new CourseEntity("Course-" + 4, "forTest");

    @Test
    public void create() {
        courseJpa.save(FOR_CREATION);
        assertNotEquals(testCourseList, courseJpa.findAll(PageRequest.of(0,4, Sort.by("id"))).getContent());
        FOR_CREATION.setId(4);
        assertEquals(Optional.of(FOR_CREATION), courseJpa.findById(4));
    }

    @Test
    public void getAll_shouldReturnAllCourses() {
        assertEquals(testCourseList, courseJpa.findAll(PageRequest.of(0,3, Sort.by("id"))).getContent());
    }

    @Test
    public void getById_shouldReturnCourseById() {
        CourseEntity expected = testCourseList.get(1);
        assertEquals(Optional.of(expected), courseJpa.findById(2));
    }

    @Test
    public void update_shouldUpdateCourse() {
        CourseEntity expected = new CourseEntity(1, "Course-" + 1, "updated");
        courseJpa.save(expected);
        assertEquals(Optional.of(expected), courseJpa.findById(1));
    }

    @Test
    public void delete_shouldDeleteCourse() {
        courseJpa.save(FOR_CREATION);
        assertNotEquals(testCourseList, courseJpa.findAll(PageRequest.of(0,4, Sort.by("id"))).getContent());

        courseJpa.deleteById(4);

        assertEquals(testCourseList, courseJpa.findAll(PageRequest.of(0,4, Sort.by("id"))).getContent());
    }

    @Test
    public void getByGroupId() {
        List<CourseEntity> expected = testCourseList.subList(0, 2);
        assertEquals(expected, courseJpa.findByGroups_id(1));
    }
}
