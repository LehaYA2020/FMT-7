package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Student;
import ru.fmt.university.service.implementation.LessonService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentServiceTest extends ServiceTest {
    @MockBean
    protected LessonService lessonServiceMock;

    @Test
    public void create_shouldCallStudentRepositoryCreatedMethod() {
        studentService.create(expectedStudent);
        verify(studentRepository).create(expectedStudent);
    }

    @Test
    public void getAll_shouldCallStudentRepositoryGetAllMethod() {
        when(studentRepository.getAll()).thenReturn(expectedStudents);
        List<Student> actualStudents = studentService.getAll();

        verify(studentRepository).getAll();
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    public void getById_shouldCallStudentRepositoryGetByIdMethod() {
        when(studentRepository.getById(1)).thenReturn(expectedStudent);
        Student actualStudent = studentService.getById(1);

        verify(studentRepository).getById(1);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void update_shouldCallStudentRepositoryUpdateMethod() {
        when(studentRepository.update(expectedStudent)).thenReturn(expectedStudent);
        Student updatedStudent = studentService.update(expectedStudent);

        verify(studentRepository).update(expectedStudent);
        assertEquals(expectedStudent, updatedStudent);
    }

    @Test
    public void delete_shouldCallStudentRepositoryDeleteMethod() {
        studentService.delete(1);
        verify(studentRepository).delete(1);
    }

    @Test
    public void assignToGroup_shouldCallStudentRepositoryGetByIdMethod() {
        studentService.assignStudentToGroup(expectedStudent.getId(), expectedGroup.getId());
        verify(studentRepository).assignToGroup(expectedStudent.getId(), expectedGroup.getId());
    }

    @Test
    public void deleteFromGroup_shouldCallStudentRepositoryDeleteFromGroupMethod() {
        studentService.updateGroupAssignment(expectedStudent.getId(), expectedGroup.getId());
        verify(studentRepository).updateGroupAssignment(expectedStudent.getId(), expectedGroup.getId());
    }

    @Test
    public void getSchedule_shouldCallLessonServiceGetLessonsByStudentMethod() {
        when(lessonServiceMock.getLessonsByStudent(expectedStudent.getId())).thenReturn(expectedLessons);
        List<Lesson> actualStudents = studentService.getSchedule(expectedStudent.getId());

        verify(lessonServiceMock).getLessonsByStudent(expectedStudent.getId());
        assertEquals(expectedLessons, actualStudents);
    }

    @Test
    public void getByGroup_shouldCallStudentRepositoryGetByGroupMethod() {
        when(studentRepository.getByGroupId(1)).thenReturn(expectedStudents);
        List<Student> actualStudents = studentService.getByGroup(1);

        verify(studentRepository).getByGroupId(1);
        assertEquals(expectedStudents, actualStudents);
    }
}
