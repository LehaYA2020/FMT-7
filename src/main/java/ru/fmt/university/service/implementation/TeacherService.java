package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fmt.university.dao.interfaces.ITeacherRepository;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Teacher;
import ru.fmt.university.service.ILessonService;
import ru.fmt.university.service.ITeacherService;
import ru.fmt.university.service.util.TeacherMapper;

import java.util.List;

@Component
@Log4j2
public class TeacherService implements ITeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    private ITeacherRepository teacherRepository;
    @Autowired
    private ILessonService lessonService;

    public void create(Teacher teacher) {
        log.debug("TeacherService calls teacherRepository.create({}).", teacher.getId());
        teacherRepository.saveAndFlush(teacherMapper.toEntity(teacher));
    }

    public Teacher getById(Integer id) {
        log.debug("TeacherService calls teacherRepository.getById({}).", id);
        return teacherMapper.toTeacher(teacherRepository.getById(id));
    }

    public List<Teacher> getAll() {
        log.debug("TeacherService calls teacherRepository.getAll().");
        return teacherMapper.toTeacher(teacherRepository.findAll());
    }

    public Teacher update(Teacher forUpdate) {
        log.debug("TeacherService calls teacherRepository.update({}).", forUpdate.getId());
        return teacherMapper.toTeacher(teacherRepository.saveAndFlush(teacherMapper.toEntity(forUpdate)));
    }

    public boolean deleteById(Integer id) {
        log.debug("TeacherService calls teacherRepository.delete({}).", id);
        teacherRepository.deleteById(id);
        return true;
    }

    public Teacher getByLesson(Integer lessonId) {
        log.debug("TeacherService calls teacherRepository.getByLesson({}).", lessonId);
        return teacherMapper.toTeacher(teacherRepository.findByLessons_id(lessonId));
    }

    public List<Teacher> getByCourse(Integer courseId) {
        log.debug("TeacherService calls teacherRepository.getByCourse({}).", courseId);
        return teacherMapper.toTeacher(teacherRepository.findByCourse_id(courseId));
    }

    public List<Lesson> getSchedule(Integer teacherId) {
        log.debug("StudentService calls lessonService.getLessonsByStudent({}).", teacherId);
        return lessonService.getLessonsByTeacher(teacherId);
    }
}
