package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Student;
import ru.fmt.university.service.implementation.LessonService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentServiceTest extends ServiceTest {
    @MockBean
    protected LessonService lessonServiceMock;

    @Test
    public void create_shouldCallStudentRepositoryCreatedMethod() {
        studentService.create(expectedStudent);
        verify(studentRepository).save(studentMapper.toEntity(expectedStudent));
    }

    @Test
    public void getAll_shouldCallStudentRepositoryGetAllMethod() {
        when(studentRepository.findAll()).thenReturn(studentMapper.toEntity(expectedStudents));
        List<Student> actualStudents = studentService.getAll();

        verify(studentRepository).findAll();
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    public void getById_shouldCallStudentRepositoryGetByIdMethod() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(studentMapper.toEntity(expectedStudent)));
        Student actualStudent = studentService.getById(1);

        verify(studentRepository).findById(1);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void update_shouldCallStudentRepositoryUpdateMethod() {
        when(studentRepository.save(studentMapper.toEntity(expectedStudent)))
                .thenReturn(studentMapper.toEntity(expectedStudent));
        Student updatedStudent = studentService.update(expectedStudent);

        verify(studentRepository).save(studentMapper.toEntity(expectedStudent));
        assertEquals(expectedStudent, updatedStudent);
    }

    @Test
    public void delete_shouldCallStudentRepositoryDeleteMethod() {
        studentService.deleteById(1);
        verify(studentRepository).deleteById(1);
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
        when(studentRepository.findByGroup_Id(1)).thenReturn(studentMapper.toEntity(expectedStudents));
        List<Student> actualStudents = studentService.getByGroup(1);

        verify(studentRepository).findByGroup_Id(1);
        assertEquals(expectedStudents, actualStudents);
    }
}
