package ru.fmt.university.dao.interfaces;

import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.GenericRepository;
import ru.fmt.university.model.entity.StudentEntity;

import java.util.List;

@Repository
public interface StudentRepository extends GenericRepository<StudentEntity, Integer> {
    void assignToGroup(Integer studentId, Integer groupId);

    void updateGroupAssignment(Integer studentId, Integer groupId);

    List<StudentEntity> findByGroup_Id(Integer groupId);

    void deleteFromGroup(Integer studentId, Integer groupId);
}
