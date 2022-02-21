package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.model.entity.LessonEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class LessonRepositoryHibernateImplTest extends RepositoryTest {
    private static final LessonEntity FOR_UPDATE = new LessonEntity(2, testCourseList.get(1), testTeacherList.get(0), 10,
            DayOfWeek.THURSDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE);

    private static final LessonEntity lesson = new LessonEntity(testCourseList.get(1), testTeacherList.get(0), 10,
            DayOfWeek.THURSDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE);

    @Test
    public void create() {
        lessonRepositoryHibernate.save(lesson);
        assertNotEquals(testLessonList.size(), lessonRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent().size());
        LessonEntity actual = lessonRepositoryHibernate.findById(4).get();
        lesson.setId(4);
        assertEquals(lesson, actual);
    }

    @Test
    public void getAll_shouldReturnAllLessonsFromDb() {
        assertEquals(testLessonList, lessonRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void getById() {
        assertEquals(testLessonList.get(0).getTeacher(), lessonRepositoryHibernate.findById(1).get().getTeacher());
    }

    @Test
    public void getById_shouldReturnEmptyOptional() {
        assertTrue(lessonRepositoryHibernate.findById(10).isEmpty());
    }

    @Test
    public void update_shouldUpdateLessonInDb() {
        lessonRepositoryHibernate.save(FOR_UPDATE);
        assertEquals(FOR_UPDATE, lessonRepositoryHibernate.findById(2).get());
    }

    @Test
    public void delete_shouldDeleteLessonFromDb() {
        lessonRepositoryHibernate.deleteById(3);
        assertEquals(testLessonList.subList(0, 2), lessonRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void getByTeacher() {
        assertEquals(testLessonList.subList(0, 2), lessonRepositoryHibernate.findByTeacher_id(testTeacherList.get(0).getId()));
    }

    @Test
    public void getByCourse() {
        assertEquals(testLessonList.subList(1, 3), lessonRepositoryHibernate.findByCourse_id(testCourseList.get(1).getId()));
    }

    @Test
    public void getByStudent() {
        assertEquals(testLessonList.subList(0, 2), lessonRepositoryHibernate.findByStudents_id(1));
    }

    @Test
    public void getByGroup() {
        assertEquals(testLessonList.subList(0, 2), lessonRepositoryHibernate.findByGroups_id(1));
    }
}
