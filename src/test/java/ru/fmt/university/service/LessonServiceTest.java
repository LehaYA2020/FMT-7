package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import ru.fmt.university.model.dto.Lesson;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LessonServiceTest extends ServiceTest {

    @Test
    public void create_shouldCallLessonRepositoryCreateMethod() {
        lessonService.create(expectedLesson);
        verify(lessonRepository).create(expectedLesson);
    }

    @Test
    public void getAll_shouldCallLessonRepositoryGetAllMethod() {
        when(lessonRepository.getAll()).thenReturn(expectedLessons);
        List<Lesson> actualLessons = lessonService.getAll();

        verify(lessonRepository).getAll();
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getById_shouldCallLessonRepositoryGetByIdMethod() {
        when(lessonRepository.getById(1)).thenReturn(expectedLesson);
        Lesson actualLesson = lessonService.getById(1);

        verify(lessonRepository).getById(1);
        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    public void update_shouldCallLessonRepositoryUpdateMethod() {
        when(lessonRepository.update(expectedLesson)).thenReturn(expectedLesson);
        Lesson updatedLesson = lessonService.update(expectedLesson);

        verify(lessonRepository).update(expectedLesson);
        assertEquals(expectedLesson, updatedLesson);
    }

    @Test
    public void delete_shouldCallLessonRepositoryDeleteMethod() {
        lessonService.delete(1);
        verify(lessonRepository).delete(1);
    }

    @Test
    public void getByStudent_shouldCallLessonRepositoryGetByStudentMethod() {
        when(lessonRepository.getByStudent(expectedStudent.getId())).thenReturn(expectedLessons);
        List<Lesson> actualLessons = lessonService.getLessonsByStudent(expectedStudent.getId());

        verify(lessonRepository).getByStudent(expectedStudent.getId());
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getByCourse_shouldCallLessonRepositoryGetByCourseMethod() {
        when(lessonRepository.getByCourse(expectedCourse.getId())).thenReturn(expectedLessons);
        List<Lesson> actualLessons = lessonService.getLessonsByCourse(expectedCourse.getId());

        verify(lessonRepository).getByCourse(expectedCourse.getId());
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getByGroup_shouldCallLessonRepositoryGetByGroupMethod() {
        when(lessonRepository.getByGroup(expectedGroup.getId())).thenReturn(expectedLessons);
        List<Lesson> actualLessons = lessonService.getLessonsByGroup(expectedGroup.getId());

        verify(lessonRepository).getByGroup(expectedGroup.getId());
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getByTeacher_shouldCallLessonRepositoryGetByTeacherMethod() {
        when(lessonRepository.getByTeacher(expectedTeacher.getId())).thenReturn(expectedLessons);
        List<Lesson> actualLessons = lessonService.getLessonsByTeacher(expectedTeacher.getId());

        verify(lessonRepository).getByTeacher(expectedTeacher.getId());
        assertEquals(expectedLessons, actualLessons);
    }
}
