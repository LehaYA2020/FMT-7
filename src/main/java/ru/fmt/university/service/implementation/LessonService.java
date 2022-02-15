package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fmt.university.dao.interfaces.ILessonRepository;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.service.ILessonService;
import ru.fmt.university.service.util.LessonMapper;

import java.util.List;

@Component
@Log4j2
public class LessonService implements ILessonService {
    @Autowired
    private ILessonRepository lessonRepository;
    @Autowired
    private LessonMapper lessonMapper;

    public void create(Lesson lesson) {
        log.debug("LessonService calls lessonRepository.create({}).", lesson.getId());
        lessonRepository.save(lessonMapper.toEntity(lesson));
    }

    public Lesson getById(Integer id) {
        log.debug("LessonService calls lessonRepository.getById({}).", id);
        return lessonMapper.toLesson(lessonRepository.getById(id));
    }

    public List<Lesson> getAll() {
        log.debug("LessonService calls lessonRepository.getAll().");
        return lessonMapper.toLesson(lessonRepository.findAll());
    }

    public Lesson update(Lesson forUpdate) {
        log.debug("LessonService calls lessonRepository.update({}).", forUpdate.getId());
        return lessonMapper.toLesson(lessonRepository.save(lessonMapper.toEntity(forUpdate)));
    }

    public boolean deleteById(Integer id) {
        log.debug("LessonService calls lessonRepository.delete({}).", id);
        lessonRepository.deleteById(id);
        return true;
    }

    public List<Lesson> getLessonsByStudent(Integer studentId) {
        log.debug("LessonService calls lessonRepository.getByStudent({}).", studentId);
        return lessonMapper.toLesson(lessonRepository.findByStudents_id(studentId));
    }

    public List<Lesson> getLessonsByCourse(Integer courseId) {
        log.debug("LessonService calls lessonRepository.getByCourse({}).", courseId);
        return lessonMapper.toLesson(lessonRepository.findByCourse_id(courseId));
    }

    public List<Lesson> getLessonsByGroup(Integer groupId) {
        log.debug("LessonService calls lessonRepository.getByGroup({}).", groupId);
        return lessonMapper.toLesson(lessonRepository.findByGroups_id(groupId));
    }

    public List<Lesson> getLessonsByTeacher(Integer teacherId) {
        log.debug("LessonService calls lessonRepository.getByTeacher({}).", teacherId);
        return lessonMapper.toLesson(lessonRepository.findByTeacher_id(teacherId));
    }
}
