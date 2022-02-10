package ru.fmt.university.service;

import ru.fmt.university.model.dto.Lesson;

import java.util.List;

public interface ILessonService extends IService<Lesson, Integer> {
    List<Lesson> getLessonsByStudent(Integer studentId);

    List<Lesson> getLessonsByCourse(Integer courseId);

    List<Lesson> getLessonsByGroup(Integer groupId);

    List<Lesson> getLessonsByTeacher(Integer teacherId);
}
