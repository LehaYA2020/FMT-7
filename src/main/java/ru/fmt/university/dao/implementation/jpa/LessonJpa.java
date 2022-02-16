package ru.fmt.university.dao.implementation.jpa;

import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.LessonRepository;
import ru.fmt.university.model.entity.LessonEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface LessonJpa extends JpaRepository<LessonEntity, Integer>, LessonRepository {

    @Query(value = "select st.group.lessons FROM StudentEntity st where st.id=:studentId")
    List<LessonEntity> findByStudents_id(@Param("studentId") Integer studentId);

    List<LessonEntity> findByTeacher_id(Integer teacherId);

    List<LessonEntity> findByGroups_id(Integer groupId);

    List<LessonEntity> findByCourse_id(Integer courseId);
}
