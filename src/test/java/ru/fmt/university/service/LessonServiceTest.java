package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.fmt.university.model.dto.Group;
import ru.fmt.university.model.dto.Lesson;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LessonServiceTest extends ServiceTest {

    @Test
    public void create_shouldCallLessonRepositoryCreateMethod() {
        when(lessonRepository.save(lessonMapper.toEntity(expectedLesson))).thenReturn(lessonMapper.toEntity(expectedLesson));
        lessonService.create(expectedLesson);
        verify(lessonRepository).save(lessonMapper.toEntity(expectedLesson));
    }

    @Test
    public void getAll_shouldCallLessonRepositoryGetAllMethod() {
        Pageable pageable = PageRequest.of(0, 10);
        when(lessonRepository.findAll(pageable)).thenReturn(new PageImpl<>(lessonMapper.toEntity(expectedLessons), pageable, pageable.getPageSize()));
        Page<Lesson> page = lessonService.getAll(pageable);
        List<Lesson> actualLessons = page.getContent();

        verify(lessonRepository).findAll(pageable);
        assertEquals(expectedLessons, actualLessons);
    }

    @Test
    public void getById_shouldCallLessonRepositoryGetByIdMethod() {
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lessonMapper.toEntity(expectedLesson)));
        Lesson actualLesson = lessonService.getById(1);

        verify(lessonRepository).findById(1);
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
