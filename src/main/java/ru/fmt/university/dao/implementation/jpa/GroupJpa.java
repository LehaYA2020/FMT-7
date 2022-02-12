package ru.fmt.university.dao.implementation.jpa;

import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.interfaces.IGroupRepository;
import ru.fmt.university.model.entity.GroupEntity;

import java.util.List;

@Repository
@ConditionalOnProperty(name = "daoImpl", havingValue = "jpa")
public interface GroupJpa extends CrudRepository<GroupEntity, Integer>, IGroupRepository {
    @Query(nativeQuery = true, value = "insert into groups_courses(group_id, course_id) values(:groupId, :courseId);")
    void assignToCourse(@Param("groupId") Integer groupId, @Param("courseId") Integer courseId);

    @Query(nativeQuery = true, value = "delete from groups_courses where group_id=:groupId and course_id=:courseId);")
    void deleteFromCourse(@Param("groupId") Integer groupId, @Param("courseId") Integer courseId);

    List<GroupEntity> findByLessons_Id(int lessonId);

    GroupEntity findByStudents_Id(int studentId);

    List<GroupEntity> findByCourses_Id(Integer courseId);

    @Query(nativeQuery = true, value = "insert into lessons_groups(lesson_id, group_id) values(:lessonId, :groupId);")
    void assignToLesson(@Param("lessonId") Integer lessonId, @Param("groupId") Integer groupId);

    @Query(nativeQuery = true, value = "delete from lessons_groups where lesson_id=:lessonId and group_id=:groupId);")
    void deleteFromLesson(@Param("lessonId") Integer lessonId, @Param("groupId") Integer groupId);
}
