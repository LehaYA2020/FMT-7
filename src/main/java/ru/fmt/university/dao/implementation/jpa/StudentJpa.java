package ru.fmt.university.dao.implementation.jpa;

import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.StudentRepository;
import ru.fmt.university.model.entity.StudentEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface StudentJpa extends CrudRepository<StudentEntity, Integer>, StudentRepository {
    @Modifying
    @Query(nativeQuery = true, value = "update students set group_id=:groupId where id=:studentId")
    void assignToGroup(@Param("studentId") Integer studentId, @Param("groupId") Integer groupId);

    @Modifying
    @Query(nativeQuery = true, value = "update students set group_id=:groupId where id=:studentId")
    void updateGroupAssignment(@Param("studentId") Integer studentId, @Param("groupId") Integer groupId);

    List<StudentEntity> findByGroup_Id(Integer groupId);

    @Modifying
    @Query(nativeQuery = true, value = "update students set group_id=null where id=:studentId and group_id=:groupId")
    void deleteFromGroup(@Param("studentId") Integer studentId, @Param("groupId") Integer groupId);
}
