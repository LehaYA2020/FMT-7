package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.model.dto.Lesson;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class LessonRepositoryHibernateImplTest extends RepositoryTest {
    private static final Lesson FOR_UPDATE = new Lesson(2, 2, 1, 10,
            DayOfWeek.THURSDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE);

    private static final Lesson lesson = new Lesson( testCourseList.get(1).getId(), testTeacherList.get(0).getId(), 10,
            DayOfWeek.THURSDAY, LocalTime.of(9, 30, 0), LessonType.LECTURE);

    @Test
    public void create() {
        lessonRepositoryHibernate.create(lesson);
        assertNotEquals(testLessonList.size(), lessonRepositoryHibernate.getAll().size());
        Lesson actual = lessonRepositoryHibernate.getById(4);
        lesson.setId(4);
        assertEquals(lesson, actual);
    }

    @Test
    public void getAll_shouldReturnAllLessonsFromDb() {
        assertEquals(testLessonList, lessonRepositoryHibernate.getAll());
    }

    @Test
    public void getById() {
        assertEquals(testLessonList.get(0), lessonRepositoryHibernate.getById(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> lessonRepositoryHibernate.getById(10));

        assertEquals(MessagesConstants.CANNOT_GET_LESSON_BY_ID, exception.getMessage());
    }

    @Test
    public void update_shouldUpdateLessonInDb() {
        lessonRepositoryHibernate.update(FOR_UPDATE);
        assertEquals(FOR_UPDATE, lessonRepositoryHibernate.getById(2));
    }

    @Test
    public void delete_shouldDeleteLessonFromDb() {
        lessonRepositoryHibernate.delete(3);
        assertEquals(testLessonList.subList(0, 2), lessonRepositoryHibernate.getAll());
    }

    @Test
    public void getByTeacher() {
        assertEquals(testLessonList.subList(0, 2), lessonRepositoryHibernate.getByTeacher(testTeacherList.get(0).getId()));
    }

    @Test
    public void getByCourse() {
        assertEquals(testLessonList.subList(1, 3), lessonRepositoryHibernate.getByCourse(testCourseList.get(1).getId()));
    }

    @Test
    public void getByStudent() {
        assertEquals(testLessonList.subList(0, 2), lessonRepositoryHibernate.getByStudent(1));
    }

    @Test
    public void getByGroup() {
        assertEquals(testLessonList.subList(0, 2), lessonRepositoryHibernate.getByGroup(1));
    }
}
