package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.entity.TeacherEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest(properties = {"daoImpl=jpa"})
public class TeacherJpaTest extends RepositoryTest {
    private static final TeacherEntity FOR_CREATION = new TeacherEntity("T-4", "Teacher", testCourseList.get(1));

    @Test
    public void create() {
        teacherJpa.save(FOR_CREATION);
        assertNotEquals(testTeacherList, teacherJpa.findAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, teacherJpa.findById(FOR_CREATION.getId()).get());
    }

    @Test
    public void getAll() {
        assertEquals(testTeacherList, teacherJpa.findAll());
    }

    @Test
    public void getById() {
        assertEquals(testTeacherList.get(0), teacherJpa.findById(1).get());
    }

    @Test
    public void delete() {
        teacherJpa.deleteById(3);
        assertEquals(testTeacherList.subList(0, 2), teacherJpa.findAll());
    }

    @Test
    public void update() {
        TeacherEntity teacher = new TeacherEntity(2, "T-" + 2, "updated", testCourseList.get(1));
        teacherJpa.save(teacher);
        assertEquals(teacher, teacherJpa.findById(2).get());
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
