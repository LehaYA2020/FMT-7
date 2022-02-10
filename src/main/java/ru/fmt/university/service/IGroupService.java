package ru.fmt.university.service;

import ru.fmt.university.model.dto.Group;

import java.util.List;

public interface IGroupService extends IService<Group, Integer> {
    boolean assignToCourse(Integer groupId, Integer courseId);

    boolean deleteFromCourse(Integer groupId, Integer courseId);

    List<Group> getByCourse(Integer courseId);

    boolean assignToLesson(Integer lessonId, Integer groupId);

    boolean deleteFromLesson(Integer lessonId, Integer groupId);

    List<Group> getByLesson(Integer lessonId);

    Group getByStudent(Integer studentId);
}
