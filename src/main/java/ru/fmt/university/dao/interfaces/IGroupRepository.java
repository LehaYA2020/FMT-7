package ru.fmt.university.dao.interfaces;

import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.IRepository;
import ru.fmt.university.model.dto.Group;

import java.util.List;

@Repository
public interface IGroupRepository extends IRepository<Group, Integer> {
    boolean assignToCourse(Integer groupId, Integer courseId);

    boolean deleteFromCourse(Integer groupId, Integer courseId);

    List<Group> getByLesson(int lessonId);

    Group getByStudent(int studentId);

    List<Group> getByCourse(Integer courseId);

    boolean assignToLesson(Integer lessonId, Integer groupId);

    boolean deleteFromLesson(Integer lessonId, Integer groupId);
}
