package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.fmt.university.dao.interfaces.LessonRepository;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.service.exception.NotFoundException;
import ru.fmt.university.service.util.LessonMapper;

import java.util.List;

@Service
@Log4j2
public class LessonService implements ru.fmt.university.service.LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonMapper lessonMapper;

    public Lesson create(Lesson lesson) {
        log.debug("LessonService calls lessonRepository.create({}).", lesson.getId());
        return lessonMapper.toLesson(lessonRepository.save(lessonMapper.toEntityForCreation(lesson)));
    }

    public Lesson getById(Integer id) {
        log.debug("LessonService calls lessonRepository.getById({}).", id);
        return lessonRepository.findById(id)
                .map(lessonMapper::toLesson)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
    }

    public Page<Lesson> getAll(Pageable pageable) {
        log.debug("LessonService calls lessonRepository.getAll().");
        return lessonMapper.toDtoPage(lessonRepository.findAll(pageable));
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
