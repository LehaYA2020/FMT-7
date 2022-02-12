package ru.fmt.university.dao.interfaces;

import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.entity.LessonEntity;

import java.util.List;

@Repository
public interface ILessonRepository extends IRepository<LessonEntity, Integer> {
    List<LessonEntity> findByStudents_id(Integer studentId);

    List<LessonEntity> findByTeacher_id(Integer teacherId);

    List<LessonEntity> findByGroups_id(Integer groupId);

    List<LessonEntity> findByCourse_id(Integer courseId);
}
