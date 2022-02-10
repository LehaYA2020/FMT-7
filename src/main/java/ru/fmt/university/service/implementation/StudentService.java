package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fmt.university.dao.interfaces.IStudentRepository;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Student;
import ru.fmt.university.service.ILessonService;
import ru.fmt.university.service.IStudentService;

import java.util.List;

@Component
@Log4j2
public class StudentService implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private ILessonService lessonService;

    public void create(Student student) {
        log.debug("StudentService calls studentRepository.create({}).", student.getId());
        studentRepository.create(student);
    }

    public Student getById(Integer id) {
        log.debug("StudentService calls studentRepository.getById({}).", id);
        return studentRepository.getById(id);
    }

    public List<Student> getAll() {
        log.debug("StudentService calls studentRepository.getAll().");
        return studentRepository.getAll();
    }

    public boolean delete(Integer id) {
        log.debug("StudentService calls studentRepository.delete({}).", id);
        return studentRepository.delete(id);
    }

    public Student update(Student forUpdate) {
        log.debug("StudentService calls studentRepository.update({}).", forUpdate.getId());
        return studentRepository.update(forUpdate);
    }

    public boolean assignStudentToGroup(Integer studentId, Integer groupId) {
        log.debug("StudentService calls studentRepository.assignToGroup({},{}).", studentId, groupId);
        return studentRepository.assignToGroup(studentId, groupId);
    }

    public boolean updateGroupAssignment(Integer studentId, Integer groupId) {
        log.debug("StudentService calls studentRepository.updateGroupAssignment({},{}).", studentId, groupId);
        return studentRepository.updateGroupAssignment(studentId, groupId);
    }

    public List<Student> getByGroup(Integer groupId) {
        log.debug("StudentService calls studentRepository.getByGroupId({}).", groupId);
        return studentRepository.getByGroupId(groupId);
    }

    public boolean deleteFromGroup(Integer studentId, Integer groupId) {
        log.debug("StudentService calls studentRepository.deleteFromGroup({},{}).", studentId, groupId);
        return studentRepository.deleteFromGroup(studentId, groupId);
    }

    public List<Lesson> getSchedule(Integer studentId) {
        log.debug("StudentService calls lessonService.getLessonsByStudent({}).", studentId);
        return lessonService.getLessonsByStudent(studentId);
    }
}
