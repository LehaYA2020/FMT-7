package ru.fmt.university.dao.implementation.jpa;

import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.IStudentRepository;
import ru.fmt.university.model.dto.Student;
import ru.fmt.university.model.entity.StudentEntity;

import java.util.List;

@Repository
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface StudentJpa extends CrudRepository<StudentEntity, Integer>, IStudentRepository {
    @Query(nativeQuery = true,value = "update students set group_id=:groupId where id=:studentId")
    void assignToGroup(@Param("studentId") Integer studentId, @Param("groupId")Integer groupId);

    @Query(nativeQuery = true,value = "update students set group_id=:groupId where id=:studentId")
    void updateGroupAssignment(@Param("studentId") Integer studentId, @Param("groupId")Integer groupId);

    List<StudentEntity> findByGroup_Id(Integer groupId);
    @Query(nativeQuery = true,value = "update students set group_id=:null where id=:studentId and group_id=:groupId")
    void deleteFromGroup(@Param("studentId") Integer studentId, @Param("groupId")Integer groupId);
}
