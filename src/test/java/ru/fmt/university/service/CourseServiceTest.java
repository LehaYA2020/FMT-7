package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.fmt.university.model.dto.Course;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class CourseServiceTest extends ServiceTest {
    @Test
    public void create_shouldCallCourseRepositoryCreateMethod() {
        when(courseRepository.save(courseMapper.toEntity(expectedCourse))).thenReturn(courseMapper.toEntity(expectedCourse));
        courseService.create(expectedCourse);
        verify(courseRepository).save(courseMapper.toEntity(expectedCourse));
    }

    @Test
    public void getAll_shouldCallCourseRepositoryGetAllMethod() {
        Pageable pageable = PageRequest.of(0, 10);
        when(courseRepository.findAll(pageable)).thenReturn(new PageImpl<>(courseMapper.toEntity(expectedCourses), pageable, pageable.getPageSize()));
        Page<Course> page = courseService.getAll(pageable);
        List<Course> actualCourses = page.getContent();

        verify(courseRepository).findAll(pageable);
        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    public void getById_shouldCallCourseRepositoryGetByIdMethod() {
        when(courseRepository.findById(1)).thenReturn(Optional.of(courseMapper.toEntity(expectedCourse)));
        Course actualCourse = courseService.getById(1);

        verify(courseRepository).findById(1);
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void update_shouldCallCourseRepositoryUpdateMethod() {
        when(courseRepository.save(courseMapper.toEntity(expectedCourse))).thenReturn(courseMapper.toEntity(expectedCourse));
        Course updatedCourse = courseService.update(expectedCourse);

        verify(courseRepository).save(courseMapper.toEntity(expectedCourse));
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
        Collection<Course> actualCourses = courseService.getByGroupId(1);

        verify(courseRepository).findByGroups_id(1);
        assertEquals(expectedCourses, actualCourses);
    }
}
