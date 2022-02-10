package ru.fmt.university.dao.interfaces;

import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.dto.Teacher;

import java.util.List;

public interface ITeacherRepository extends IRepository<Teacher, Integer> {
    List<Teacher> getByCourse(Integer courseId);

    Teacher getByLesson(Integer lessonId);
}
