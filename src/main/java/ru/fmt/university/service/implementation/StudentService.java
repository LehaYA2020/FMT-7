package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.fmt.university.dao.interfaces.StudentRepository;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Student;
import ru.fmt.university.service.LessonService;
import ru.fmt.university.service.exception.NotFoundException;
import ru.fmt.university.service.util.StudentMapper;

import java.util.List;

@Service
@Log4j2
public class StudentService implements ru.fmt.university.service.StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private StudentMapper studentMapper;

    public Student create(Student student) {
        log.debug("StudentService calls studentRepository.create({}).", student.getId());
        return studentMapper.toStudent(studentRepository.save(studentMapper.toEntity(student)));
    }

    public Page<Student> getAll(Pageable pageable) {
        log.debug("StudentService calls studentRepository.getAll().");
        return studentMapper.toDtoPage(studentRepository.findAll(pageable));
    }

    public Student getById(Integer id) {
        log.debug("StudentService calls studentRepository.getById({}).", id);
        return studentRepository.findById(id)
                .map(studentMapper::toStudent)
                .orElseThrow(() -> new NotFoundException("Course not found"));
    }

    public boolean deleteById(Integer id) {
        log.debug("StudentService calls studentRepository.delete({}).", id);
        studentRepository.deleteById(id);
        return true;
    }

    public Student update(Student forUpdate) {
        log.debug("StudentService calls studentRepository.update({}).", forUpdate.getId());
        return studentMapper.toStudent(studentRepository.save(studentMapper.toEntity(forUpdate)));
    }

    public boolean assignStudentToGroup(Integer studentId, Integer groupId) {
        log.debug("StudentService calls studentRepository.assignToGroup({},{}).", studentId, groupId);
        studentRepository.assignToGroup(studentId, groupId);
        return true;
    }

    public boolean updateGroupAssignment(Integer studentId, Integer groupId) {
        log.debug("StudentService calls studentRepository.updateGroupAssignment({},{}).", studentId, groupId);
        studentRepository.updateGroupAssignment(studentId, groupId);
        return true;
    }

    public List<Student> getByGroup(Integer groupId) {
        log.debug("StudentService calls studentRepository.getByGroupId({}).", groupId);
        return studentMapper.toStudent(studentRepository.findByGroup_Id(groupId));
    }

    public boolean deleteFromGroup(Integer studentId, Integer groupId) {
        log.debug("StudentService calls studentRepository.deleteFromGroup({},{}).", studentId, groupId);
        studentRepository.deleteFromGroup(studentId, groupId);
        return true;
    }

    public List<Lesson> getSchedule(Integer studentId) {
        log.debug("StudentService calls lessonService.getLessonsByStudent({}).", studentId);
        return lessonService.getLessonsByStudent(studentId);
    }
}
