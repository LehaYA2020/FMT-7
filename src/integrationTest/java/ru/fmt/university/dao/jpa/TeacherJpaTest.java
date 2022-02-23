package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.entity.TeacherEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest(properties = {"daoImpl=jpa"})
public class TeacherJpaTest extends RepositoryTest {
    private static final TeacherEntity FOR_CREATION = new TeacherEntity("T-4", "Teacher", testCourseList.get(1));

    @Test
    public void create() {
        teacherJpa.save(FOR_CREATION);
        assertNotEquals(testTeacherList, teacherJpa.findAll(PageRequest.of(0, 10)).getContent());
        FOR_CREATION.setId(4);
        assertEquals(Optional.of(FOR_CREATION), teacherJpa.findById(FOR_CREATION.getId()));
    }

    @Test
    public void getAll() {
        assertEquals(testTeacherList, teacherJpa.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void getById() {
        assertEquals(Optional.of(testTeacherList.get(0)), teacherJpa.findById(1));
    }

    @Test
    public void delete() {
        teacherJpa.deleteById(3);
        assertEquals(testTeacherList.subList(0, 2), teacherJpa.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void update() {
        TeacherEntity teacher = new TeacherEntity(2, "T-" + 2, "updated", testCourseList.get(1));
        teacherJpa.save(teacher);
        assertEquals(Optional.of(teacher), teacherJpa.findById(2));
    }

    @Test
    public void getByCourse() {
        assertEquals(testTeacherList.subList(0, 2), teacherJpa.findByCourse_id(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testTeacherList.get(0), teacherJpa.findByLessons_id(1));
    }
}
