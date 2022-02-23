package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.model.entity.LessonEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest(properties = {"daoImpl=jpa"})
public class LessonJpaTest extends RepositoryTest {
    private static final LessonEntity FOR_UPDATE = new LessonEntity(2, testCourseList.get(1), testTeacherList.get(0), 10,
            DayOfWeek.THURSDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE);

    private static final LessonEntity lesson = new LessonEntity(testCourseList.get(1), testTeacherList.get(0), 10,
            DayOfWeek.THURSDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE);

    @Test
    public void create() {
        lessonJpa.save(lesson);
        assertNotEquals(testLessonList, lessonJpa.findAll(PageRequest.of(0, 10)).getContent());
        Optional<LessonEntity> actual = lessonJpa.findById(4);
        lesson.setId(4);
        assertEquals(Optional.of(lesson), actual);
    }

    @Test
    public void getAll_shouldReturnAllLessonsFromDb() {
        assertEquals(testLessonList, lessonJpa.findAll(PageRequest.of(0, 3)).getContent());
    }

    @Test
    public void getById() {
        assertEquals(Optional.of(testLessonList.get(0)), lessonJpa.findById(1));
    }

    @Test
    public void update_shouldUpdateLessonInDb() {
        lessonJpa.save(FOR_UPDATE);
        assertEquals(Optional.of(FOR_UPDATE), lessonJpa.findById(2));
    }

    @Test
    public void delete_shouldDeleteLessonFromDb() {
        lessonJpa.deleteById(3);
        assertEquals(testLessonList.subList(0, 2), lessonJpa.findAll(PageRequest.of(0, 3)).getContent());
        assertEquals(testTeacherList, teacherJpa.findAll(PageRequest.of(0, 3)).getContent());
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
