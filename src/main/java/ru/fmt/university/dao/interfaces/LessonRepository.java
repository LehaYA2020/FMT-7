package ru.fmt.university.dao.interfaces;

import ru.fmt.university.dao.GenericRepository;
import ru.fmt.university.model.entity.LessonEntity;

import java.util.List;

@org.springframework.stereotype.Repository
public interface LessonRepository extends GenericRepository<LessonEntity, Integer> {
    List<LessonEntity> findByStudents_id(Integer studentId);

    List<LessonEntity> findByTeacher_id(Integer teacherId);

    List<LessonEntity> findByGroups_id(Integer groupId);

    List<LessonEntity> findByCourse_id(Integer courseId);
}
