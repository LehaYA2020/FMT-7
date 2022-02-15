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
        verify(lessonRepository).save(lessonMapper.toEntity(expectedLesson));
    }

    @Test
    public void getAll_shouldCallLessonRepositoryGetAllMethod() {
        when(lessonRepository.findAll()).thenReturn(lessonMapper.toEntity(expectedLessons));
        List<Lesson> actualLessons = lessonService.getAll();

        verify(lessonRepository).findAll();
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getById_shouldCallLessonRepositoryGetByIdMethod() {
        when(lessonRepository.getById(1)).thenReturn(lessonMapper.toEntity(expectedLesson));
        Lesson actualLesson = lessonService.getById(1);

        verify(lessonRepository).getById(1);
        assertEquals(expectedLesson, actualLesson);
    }

    @Test
    public void update_shouldCallLessonRepositoryUpdateMethod() {
        when(lessonRepository.save(lessonMapper.toEntity(expectedLesson))).thenReturn(lessonMapper.toEntity(expectedLesson));
        Lesson updatedLesson = lessonService.update(expectedLesson);

        verify(lessonRepository).save(lessonMapper.toEntity(expectedLesson));
        assertEquals(expectedLesson, updatedLesson);
    }

    @Test
    public void delete_shouldCallLessonRepositoryDeleteMethod() {
        lessonService.deleteById(1);
        verify(lessonRepository).deleteById(1);
    }

    @Test
    public void getByStudent_shouldCallLessonRepositoryGetByStudentMethod() {
        when(lessonRepository.findByStudents_id(expectedStudent.getId())).thenReturn(lessonMapper.toEntity(expectedLessons));
        List<Lesson> actualLessons = lessonService.getLessonsByStudent(expectedStudent.getId());

        verify(lessonRepository).findByStudents_id(expectedStudent.getId());
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getByCourse_shouldCallLessonRepositoryGetByCourseMethod() {
        when(lessonRepository.findByCourse_id(expectedCourse.getId())).thenReturn(lessonMapper.toEntity(expectedLessons));
        List<Lesson> actualLessons = lessonService.getLessonsByCourse(expectedCourse.getId());

        verify(lessonRepository).findByCourse_id(expectedCourse.getId());
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getByGroup_shouldCallLessonRepositoryGetByGroupMethod() {
        when(lessonRepository.findByGroups_id(expectedGroup.getId())).thenReturn(lessonMapper.toEntity(expectedLessons));
        List<Lesson> actualLessons = lessonService.getLessonsByGroup(expectedGroup.getId());

        verify(lessonRepository).findByGroups_id(expectedGroup.getId());
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getByTeacher_shouldCallLessonRepositoryGetByTeacherMethod() {
        when(lessonRepository.findByTeacher_id(expectedTeacher.getId())).thenReturn(lessonMapper.toEntity(expectedLessons));
        List<Lesson> actualLessons = lessonService.getLessonsByTeacher(expectedTeacher.getId());

        verify(lessonRepository).findByTeacher_id(expectedTeacher.getId());
        assertEquals(expectedLessons, actualLessons);
    }
}
