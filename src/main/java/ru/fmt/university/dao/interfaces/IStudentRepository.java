package ru.fmt.university.dao.interfaces;

import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.dto.Student;

import java.util.List;

public interface IStudentRepository extends IRepository<Student, Integer> {
    boolean assignToGroup(Integer studentId, Integer groupId);

    boolean updateGroupAssignment(Integer studentId, Integer groupId);

    List<Student> getByGroupId(Integer groupId);

    boolean deleteFromGroup(Integer studentId, Integer groupId);
}
