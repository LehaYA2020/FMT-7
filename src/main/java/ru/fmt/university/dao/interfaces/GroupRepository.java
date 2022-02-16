package ru.fmt.university.dao.interfaces;

import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.entity.GroupEntity;

import java.util.List;

@Repository
public interface GroupRepository extends IRepository<GroupEntity, Integer> {
    void assignToCourse(Integer groupId, Integer courseId);

    void deleteFromCourse(Integer groupId, Integer courseId);

    List<GroupEntity> findByLessons_Id(int lessonId);

    GroupEntity findByStudents_Id(int studentId);

    List<GroupEntity> findByCourses_Id(Integer courseId);

    void assignToLesson(Integer lessonId, Integer groupId);

    void deleteFromLesson(Integer lessonId, Integer groupId);
}
