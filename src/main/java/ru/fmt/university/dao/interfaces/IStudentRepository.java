package ru.fmt.university.dao.interfaces;

import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.entity.StudentEntity;

import java.util.List;

public interface IStudentRepository extends IRepository<StudentEntity, Integer> {
    void assignToGroup(Integer studentId, Integer groupId);

    void updateGroupAssignment(Integer studentId, Integer groupId);

    List<StudentEntity> findByGroup_Id(Integer groupId);

    void deleteFromGroup(Integer studentId, Integer groupId);
}
