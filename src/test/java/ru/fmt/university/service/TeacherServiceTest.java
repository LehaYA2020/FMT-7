package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Teacher;
import ru.fmt.university.service.implementation.LessonService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TeacherServiceTest extends ServiceTest {
    @MockBean
    protected LessonService lessonServiceMock;

    @Test
    public void create_shouldCallTeacherRepositoryCreateMethod() {
        when(teacherRepository.save(teacherMapper.toEntityForCreation(expectedTeacher))).thenReturn(teacherMapper.toEntity(expectedTeacher));
        teacherService.create(expectedTeacher);
        verify(teacherRepository).save(teacherMapper.toEntityForCreation(expectedTeacher));
    }

    @Test
    public void getAll_shouldCallTeacherRepositoryGetAllMethod() {
        Pageable pageable = PageRequest.of(0, 10);
        when(teacherRepository.findAll(pageable)).thenReturn(new PageImpl<>(teacherMapper.toEntity(expectedTeachers), pageable, pageable.getPageSize()));
        Page<Teacher> page = teacherService.getAll(pageable);
        List<Teacher> actualTeachers = page.getContent();

        verify(teacherRepository).findAll(pageable);

        assertEquals(expectedTeachers, actualTeachers);
    }

    @Test
    public void getById_shouldCallTeacherRepositoryGetByIdMethod() {
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacherMapper.toEntity(expectedTeacher)));
        Teacher actualTeacher = teacherService.getById(1);

        verify(teacherRepository).findById(1);
        assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    public void update_shouldCallTeacherRepositoryUpdateMethod() {
        when(teacherRepository.save(teacherMapper.toEntity(expectedTeacher))).thenReturn(teacherMapper.toEntity(expectedTeacher));
        Teacher updatedTeacher = teacherService.update(expectedTeacher);

        verify(teacherRepository).save(teacherMapper.toEntity(expectedTeacher));
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
