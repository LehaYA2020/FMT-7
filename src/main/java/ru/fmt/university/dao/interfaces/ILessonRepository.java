package ru.fmt.university.dao.interfaces;

import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.dto.Lesson;

import java.util.List;

@Repository
public interface ILessonRepository extends IRepository<Lesson, Integer> {
    List<Lesson> getByStudent(Integer studentId);

    List<Lesson> getByTeacher(Integer teacherId);

    List<Lesson> getByGroup(Integer groupId);

    List<Lesson> getByCourse(Integer courseId);
}
