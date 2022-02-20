package ru.fmt.university.dao.implementation.jpa;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.GroupRepository;
import ru.fmt.university.model.entity.GroupEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface GroupJpa extends CrudRepository<GroupEntity, Integer>, GroupRepository {
    @Modifying
    @Query(nativeQuery = true, value = "insert into groups_courses(group_id, course_id) values(:groupId, :courseId) ;")
    void assignToCourse(@Param("groupId") Integer groupId, @Param("courseId") Integer courseId);

    @Modifying
    @Query(value = "delete from groups_courses gc where gc.group_id=:groupId and gc.course_id=:courseId", nativeQuery = true)
    void deleteFromCourse(@Param("groupId") Integer groupId, @Param("courseId") Integer courseId);

    List<GroupEntity> findByLessons_Id(int lessonId);

    GroupEntity findByStudents_Id(int studentId);

    List<GroupEntity> findByCourses_Id(Integer courseId);

    @Modifying
    @Query(nativeQuery = true, value = "insert into lessons_groups(lesson_id, group_id) values(:lessonId, :groupId);")
    void assignToLesson(@Param("lessonId") Integer lessonId, @Param("groupId") Integer groupId);

    @Modifying
    @Query(nativeQuery = true, value = "delete from lessons_groups lg where lg.lesson_id=:lessonId and lg.group_id=:groupId")
    void deleteFromLesson(@Param("lessonId") Integer lessonId, @Param("groupId") Integer groupId);
}
