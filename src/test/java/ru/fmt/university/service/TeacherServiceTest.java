package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Teacher;
import ru.fmt.university.service.implementation.LessonService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeacherServiceTest extends ServiceTest {
    @MockBean
    protected LessonService lessonServiceMock;

    @Test
    public void create_shouldCallTeacherRepositoryCreateMethod() {
        teacherService.create(expectedTeacher);
        verify(teacherRepository).saveAndFlush(teacherMapper.toEntity(expectedTeacher));
    }

    @Test
    public void getAll_shouldCallTeacherRepositoryGetAllMethod() {
        when(teacherRepository.findAll()).thenReturn(teacherMapper.toEntity(expectedTeachers));
        List<Teacher> actualTeachers = teacherService.getAll();

        verify(teacherRepository).findAll();

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    public void getById_shouldCallTeacherRepositoryGetByIdMethod() {
        when(teacherRepository.getById(1)).thenReturn(teacherMapper.toEntity(expectedTeacher));
        Teacher actualTeacher = teacherService.getById(1);

        verify(teacherRepository).getById(1);
        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    public void update_shouldCallTeacherRepositoryUpdateMethod() {
        when(teacherRepository.saveAndFlush(teacherMapper.toEntity(expectedTeacher))).thenReturn(teacherMapper.toEntity(expectedTeacher));
        Teacher updatedTeacher = teacherService.update(expectedTeacher);

        verify(teacherRepository).saveAndFlush(teacherMapper.toEntity(expectedTeacher));
        assertEquals(expectedTeacher, updatedTeacher);
    }

    @Test
    public void delete_shouldCallTeacherRepositoryDeleteMethod() {
        teacherService.deleteById(1);
        verify(teacherRepository).deleteById(1);
    }

    @Test
    public void getByCourse_shouldCallTeacherRepositoryGetByCourseMethod() {
        when(teacherRepository.findByCourse_id(expectedCourse.getId())).thenReturn(teacherMapper.toEntity(expectedTeachers));
        List<Teacher> actualTeachers = teacherService.getByCourse(expectedCourse.getId());

        verify(teacherRepository).findByCourse_id(expectedCourse.getId());
        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    public void getSchedule_shouldCallTeacherRepositoryGetScheduleMethod() {
        when(lessonServiceMock.getLessonsByTeacher(expectedTeacher.getId())).thenReturn(expectedLessons);
        List<Lesson> actualTeachers = teacherService.getSchedule(expectedTeacher.getId());

        verify(lessonServiceMock).getLessonsByTeacher(expectedTeacher.getId());
        assertEquals(expectedLessons, actualTeachers);
    }

    @Test
    public void getByLesson_shouldCallTeacherRepositoryGetByLessonMethod() {
        when(teacherRepository.findByLessons_id(expectedLesson.getId())).thenReturn(teacherMapper.toEntity(expectedTeacher));
        Teacher actualTeacher = teacherService.getByLesson(expectedLesson.getId());

        verify(teacherRepository).findByLessons_id(expectedLesson.getId());
        assertEquals(expectedTeacher, actualTeacher);
    }
}
