package ru.fmt.university.dao.interfaces;

import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.entity.TeacherEntity;

import java.util.List;

public interface ITeacherRepository extends IRepository<TeacherEntity, Integer> {
    List<TeacherEntity> findByCourse_id(Integer courseId);

    TeacherEntity findByLessons_id(Integer lessonId);
}
