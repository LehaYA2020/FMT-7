package ru.fmt.university.service;

import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Student;

import java.util.List;

public interface IStudentService extends IService<Student, Integer> {
    boolean assignStudentToGroup(Integer studentId, Integer groupId);

    boolean updateGroupAssignment(Integer studentId, Integer groupId);

    List<Student> getByGroup(Integer groupId);

    boolean deleteFromGroup(Integer studentId, Integer groupId);

    List<Lesson> getSchedule(Integer studentId);
}
