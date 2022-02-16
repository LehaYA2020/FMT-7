package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.model.entity.LessonEntity;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Transactional
@DataJpaTest(properties = {"daoImpl=jpa"})
public class LessonJpaTest extends RepositoryTest {
    private static final LessonEntity FOR_UPDATE = new LessonEntity(2, testCourseList.get(1), testTeacherList.get(0), 10,
            DayOfWeek.THURSDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE);

    private static final LessonEntity lesson = new LessonEntity(testCourseList.get(1), testTeacherList.get(0), 10,
            DayOfWeek.THURSDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE);

    @Test
    public void create() {
        lessonJpa.save(lesson);
        assertNotEquals(testLessonList.size(), lessonJpa.findAll().size());
        LessonEntity actual = lessonJpa.findById(4).get();
        lesson.setId(4);
        assertEquals(lesson, actual);
    }

    @Test
    public void getAll_shouldReturnAllLessonsFromDb() {
        assertEquals(testLessonList, lessonJpa.findAll());
    }

    @Test
    public void getById() {
        assertEquals(testLessonList.get(0), lessonJpa.findById(1).get());
    }

    @Test
    public void update_shouldUpdateLessonInDb() {
        lessonJpa.save(FOR_UPDATE);
        assertEquals(FOR_UPDATE, lessonJpa.findById(2).get());
    }

    @Test
    public void delete_shouldDeleteLessonFromDb() {
        lessonJpa.deleteById(3);
        assertEquals(testLessonList.subList(0, 2), lessonJpa.findAll());
        assertEquals(testTeacherList, teacherJpa.findAll());
    }

    @Test
    public void getByTeacher() {
        assertEquals(testLessonList.subList(0, 2), lessonJpa.findByTeacher_id(testTeacherList.get(0).getId()));
    }

    @Test
    public void getByCourse() {
        assertEquals(testLessonList.subList(1, 3), lessonJpa.findByCourse_id(testCourseList.get(1).getId()));
    }

    @Test
    public void getByStudent() {
        assertEquals(testLessonList.subList(0, 2), lessonJpa.findByStudents_id(1));
    }

    @Test
    public void getByGroup() {
        assertEquals(testLessonList.subList(0, 2), lessonJpa.findByGroups_id(1));
    }
}
