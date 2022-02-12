package ru.fmt.university.dao.implementation.jpa;

import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.ILessonRepository;
import ru.fmt.university.model.entity.LessonEntity;

import java.util.List;

@Repository
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface LessonJpa extends CrudRepository<LessonEntity, Integer>, ILessonRepository {

    @Query(value = "select st.group.lessons FROM StudentEntity st, GroupEntity where st.id=:studentId")
    List<LessonEntity> findByStudents_id(@Param("studentId") Integer studentId);

    List<LessonEntity> findByTeacher_id(Integer teacherId);

    List<LessonEntity> findByGroups_id(Integer groupId);

    List<LessonEntity> findByCourse_id(Integer courseId);
}
