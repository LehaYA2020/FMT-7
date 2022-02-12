package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import ru.fmt.university.model.dto.Course;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CourseServiceTest extends ServiceTest {

    @Test
    public void create_shouldCallCourseRepositoryCreateMethod() {
        courseService.create(expectedCourse);
        verify(courseRepository).saveAndFlush(courseMapper.toEntity(expectedCourse));
    }

    @Test
    public void getAll_shouldCallCourseRepositoryGetAllMethod() {
        when(courseRepository.findAll()).thenReturn(courseMapper.toEntity(expectedCourses));
        List<Course> actualCourses = courseService.getAll();

        verify(courseRepository).findAll();
        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    public void getById_shouldCallCourseRepositoryGetByIdMethod() {
        when(courseRepository.getById(1)).thenReturn(courseMapper.toEntity(expectedCourse));
        Course actualCourse = courseService.getById(1);

        verify(courseRepository).getById(1);
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void update_shouldCallCourseRepositoryUpdateMethod() {
        when(courseRepository.saveAndFlush(courseMapper.toEntity(expectedCourse))).thenReturn(courseMapper.toEntity(expectedCourse));
        Course updatedCourse = courseService.update(expectedCourse);

        verify(courseRepository).saveAndFlush(courseMapper.toEntity(expectedCourse));
        assertEquals(expectedCourse, updatedCourse);
    }

    @Test
    public void delete_shouldCallCourseRepositoryDeleteMethod() {
        courseService.deleteById(1);
        verify(courseRepository).deleteById(1);
    }

    @Test
    public void getByGroupId_shouldCallCourseRepositoryGetByGroupIdMethod() {
        when(courseRepository.findByGroups_id(1)).thenReturn(courseMapper.toEntity(expectedCourses));
        List<Course> actualCourses = courseService.getByGroupId(1);

        verify(courseRepository).findByGroups_id(1);
        assertEquals(expectedCourses, actualCourses);
    }
}
