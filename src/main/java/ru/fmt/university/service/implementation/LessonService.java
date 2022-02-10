package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fmt.university.dao.interfaces.ILessonRepository;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.service.ILessonService;

import java.util.List;

@Component
@Log4j2
public class LessonService implements ILessonService {
    @Autowired
    private ILessonRepository lessonRepository;

    public void create(Lesson lesson) {
        log.debug("LessonService calls lessonRepository.create({}).", lesson.getId());
        lessonRepository.create(lesson);
    }

    public Lesson getById(Integer id) {
        log.debug("LessonService calls lessonRepository.getById({}).", id);
        return lessonRepository.getById(id);
    }

    public List<Lesson> getAll() {
        log.debug("LessonService calls lessonRepository.getAll().");
        return lessonRepository.getAll();
    }

    public Lesson update(Lesson forUpdate) {
        log.debug("LessonService calls lessonRepository.update({}).", forUpdate.getId());
        return lessonRepository.update(forUpdate);
    }

    public boolean delete(Integer id) {
        log.debug("LessonService calls lessonRepository.delete({}).", id);
        return lessonRepository.delete(id);
    }

    public List<Lesson> getLessonsByStudent(Integer studentId) {
        log.debug("LessonService calls lessonRepository.getByStudent({}).", studentId);
        return lessonRepository.getByStudent(studentId);
    }

    public List<Lesson> getLessonsByCourse(Integer courseId) {
        log.debug("LessonService calls lessonRepository.getByCourse({}).", courseId);
        return lessonRepository.getByCourse(courseId);
    }

    public List<Lesson> getLessonsByGroup(Integer groupId) {
        log.debug("LessonService calls lessonRepository.getByGroup({}).", groupId);
        return lessonRepository.getByGroup(groupId);
    }

    public List<Lesson> getLessonsByTeacher(Integer teacherId) {
        log.debug("LessonService calls lessonRepository.getByTeacher({}).", teacherId);
        return lessonRepository.getByTeacher(teacherId);
    }
}
