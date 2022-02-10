package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fmt.university.dao.interfaces.IGroupRepository;
import ru.fmt.university.model.dto.Group;
import ru.fmt.university.service.IGroupService;

import java.util.List;

@Component
@Log4j2
public class GroupService implements IGroupService {
    @Autowired
    private IGroupRepository groupRepository;

    public void create(Group group) {
        log.debug("GroupService calls groupRepository.create({}).", group.getId());
        groupRepository.create(group);
    }

    public Group getById(Integer id) {
        log.debug("GroupService calls groupRepository.getById({}).", id);
        return groupRepository.getById(id);
    }

    public List<Group> getAll() {
        log.debug("GroupService calls groupRepository.getAll().");
        return groupRepository.getAll();
    }

    public Group update(Group forUpdate) {
        log.debug("GroupService calls groupRepository.update({}).", forUpdate);
        return groupRepository.update(forUpdate);
    }

    public boolean delete(Integer id) {
        log.debug("GroupService calls groupRepository.delete({}).", id);
        return groupRepository.delete(id);
    }

    public boolean assignToCourse(Integer groupId, Integer courseId) {
        log.debug("GroupService calls groupRepository.assignToCourse({}, {}).", groupId, courseId);
        return groupRepository.assignToCourse(groupId, courseId);
    }

    public boolean deleteFromCourse(Integer groupId, Integer courseId) {
        log.debug("GroupService calls groupRepository.deleteFromCourse({}, {}).", groupId, courseId);
        return groupRepository.deleteFromCourse(groupId, courseId);
    }

    public List<Group> getByCourse(Integer courseId) {
        log.debug("GroupService calls groupRepository.getByCourse({}).", courseId);
        return groupRepository.getByCourse(courseId);
    }

    public boolean assignToLesson(Integer lessonId, Integer groupId) {
        log.debug("GroupService calls groupRepository.deleteFromLesson({}, {}).", lessonId, groupId);
        return groupRepository.assignToLesson(lessonId, groupId);
    }

    public boolean deleteFromLesson(Integer lessonId, Integer groupId) {
        log.debug("GroupService calls groupRepository.deleteFromLesson({}, {}).", lessonId, groupId);
        return groupRepository.deleteFromLesson(lessonId, groupId);
    }

    public List<Group> getByLesson(Integer lessonId) {
        log.debug("GroupService calls groupRepository.getByCourse({}).", lessonId);
        return groupRepository.getByLesson(lessonId);
    }

    public Group getByStudent(Integer studentId) {
        return groupRepository.getByStudent(studentId);
    }
}
