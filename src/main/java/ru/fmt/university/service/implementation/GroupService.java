package ru.fmt.university.service.implementation;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.fmt.university.dao.interfaces.GroupRepository;
import ru.fmt.university.model.dto.Group;
import ru.fmt.university.service.IGroupService;
import ru.fmt.university.service.util.GroupMapper;

import java.util.List;

@Service
@Log4j2
public class GroupService implements IGroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMapper groupMapper;

    public void create(Group group) {
        log.debug("GroupService calls groupRepository.create({}).", group.getId());
        groupRepository.save(groupMapper.toEntity(group));
    }

    public Group getById(Integer id) {
        log.debug("GroupService calls groupRepository.getById({}).", id);
        return groupMapper.toGroup(groupRepository.findById(id).get());
    }

    public Page<Group> getAll(Pageable pageable) {
        log.debug("GroupService calls groupRepository.getAll().");
        return groupMapper.toDtoPage(groupRepository.findAll(pageable));
    }

    public Group update(Group forUpdate) {
        log.debug("GroupService calls groupRepository.update({}).", forUpdate);
        return groupMapper.toGroup(groupRepository.save(groupMapper.toEntity(forUpdate)));
    }

    public boolean deleteById(Integer id) {
        log.debug("GroupService calls groupRepository.delete({}).", id);
        groupRepository.deleteById(id);
        return true;
    }

    public boolean assignToCourse(Integer groupId, Integer courseId) {
        log.debug("GroupService calls groupRepository.assignToCourse({}, {}).", groupId, courseId);
        groupRepository.assignToCourse(groupId, courseId);
        return true;
    }

    public boolean deleteFromCourse(Integer groupId, Integer courseId) {
        log.debug("GroupService calls groupRepository.deleteFromCourse({}, {}).", groupId, courseId);
        groupRepository.deleteFromCourse(groupId, courseId);
        return true;
    }

    public List<Group> getByCourse(Integer courseId) {
        log.debug("GroupService calls groupRepository.getByCourse({}).", courseId);
        return groupMapper.toGroup(groupRepository.findByCourses_Id(courseId));
    }

    public boolean assignToLesson(Integer lessonId, Integer groupId) {
        log.debug("GroupService calls groupRepository.deleteFromLesson({}, {}).", lessonId, groupId);
        groupRepository.assignToLesson(lessonId, groupId);
        return true;
    }

    public boolean deleteFromLesson(Integer lessonId, Integer groupId) {
        log.debug("GroupService calls groupRepository.deleteFromLesson({}, {}).", lessonId, groupId);
        groupRepository.deleteFromLesson(lessonId, groupId);
        return true;
    }

    public List<Group> getByLesson(Integer lessonId) {
        log.debug("GroupService calls groupRepository.getByCourse({}).", lessonId);
        return groupMapper.toGroup(groupRepository.findByLessons_Id(lessonId));
    }

    public Group getByStudent(Integer studentId) {
        return groupMapper.toGroup(groupRepository.findByStudents_Id(studentId));
    }
}
