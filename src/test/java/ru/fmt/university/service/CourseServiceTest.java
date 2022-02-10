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
        verify(courseRepository).create(expectedCourse);
    }

    @Test
    public void getAll_shouldCallCourseRepositoryGetAllMethod() {
        when(courseRepository.getAll()).thenReturn(expectedCourses);
        List<Course> actualCourses = courseService.getAll();

        verify(courseRepository).getAll();
        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    public void getById_shouldCallCourseRepositoryGetByIdMethod() {
        when(courseRepository.getById(1)).thenReturn(expectedCourse);
        Course actualCourse = courseService.getById(1);

        verify(courseRepository).getById(1);
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void update_shouldCallCourseRepositoryUpdateMethod() {
        when(courseRepository.update(expectedCourse)).thenReturn(expectedCourse);
        Course updatedCourse = courseService.update(expectedCourse);

        verify(courseRepository).update(expectedCourse);
        assertEquals(expectedCourse, updatedCourse);
    }

    @Test
    public void delete_shouldCallCourseRepositoryDeleteMethod() {
        courseService.delete(1);
        verify(courseRepository).delete(1);
    }

    @Test
    public void getByGroupId_shouldCallCourseRepositoryGetByGroupIdMethod() {
        when(courseRepository.getByGroupId(1)).thenReturn(expectedCourses);
        List<Course> actualCourses = courseService.getByGroupId(1);

        verify(courseRepository).getByGroupId(1);
        assertEquals(expectedCourses, actualCourses);
    }
}
