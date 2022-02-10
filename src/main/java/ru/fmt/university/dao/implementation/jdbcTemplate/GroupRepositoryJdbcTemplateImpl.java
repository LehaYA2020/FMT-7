package ru.fmt.university.dao.implementation.jdbcTemplate;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.dao.interfaces.IGroupRepository;
import ru.fmt.university.dao.sources.Query;
import ru.fmt.university.dao.util.GroupMapper;
import ru.fmt.university.model.dto.Group;

import java.util.List;

@Repository
@Log4j2
@ConditionalOnProperty(name = "daoImpl", havingValue = "jdbc")
public class GroupRepositoryJdbcTemplateImpl implements IGroupRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GroupMapper groupMapper;

    public Group create(Group group) {
        log.trace("create({}).", group);
        try {
            jdbcTemplate.update(Query.INSERT_GROUP.getText(), group.getName());

        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_INSERT_GROUPS, e);
            throw new DaoException(MessagesConstants.CANNOT_INSERT_GROUPS, e);
        }
        log.debug("Group {} created.", group);
        return group;
    }

    public List<Group> getAll() {
        log.trace("getAll()");
        List<Group> groups;
        try {
            groups = jdbcTemplate.query(Query.GET_ALL_GROUPS.getText(), groupMapper);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_GROUPS, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_GROUPS, e);
        }
        log.debug("Found {}.", groups);
        return groups;
    }

    public Group getById(Integer id) {
        log.trace("getById({})", id);
        Group group;
        try {
            group = jdbcTemplate.queryForObject(Query.GET_GROUP_BY_ID.getText(), groupMapper, id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_GROUP_BY_ID, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_GROUP_BY_ID, e);
        }
        log.debug("Found {}.", group);
        return group;
    }

    public boolean delete(Integer id) {
        log.trace("delete({})", id);
        try {
            jdbcTemplate.update(Query.DELETE_GROUP.getText(), id);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP, e);
        }
        log.debug("Group with id={}.", id);
        return true;
    }

    public boolean assignToCourse(Integer groupId, Integer courseId) {
        log.trace("assignToCourse({},{})", groupId, courseId);
        try {
            jdbcTemplate.update(Query.ASSIGN_GROUP_TO_COURSE.getText(), groupId, courseId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_GROUP_TO_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_GROUP_TO_COURSE, e);
        }
        log.debug("Group {} assigned to course {}", groupId, courseId);
        return true;
    }

    public boolean deleteFromCourse(Integer groupId, Integer courseId) {
        log.trace("deleteFromCourse({}, {})", groupId, courseId);
        try {
            jdbcTemplate.update(Query.DELETE_GROUP_FROM_COURSE.getText(), groupId, courseId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP_FROM_COURSE, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP_FROM_COURSE, e);
        }
        log.debug("Course {} deleted from Course {}", groupId, courseId);
        return true;
    }

    public List<Group> getByLesson(int lessonId) {
        log.trace("getByLesson({})", lessonId);
        List<Group> groups;
        try {
            groups = jdbcTemplate.query(Query.GET_GROUPS_BY_LESSON.getText(), groupMapper, lessonId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_LESSON, e);
        }
        log.debug("Found {} by {}", groups, lessonId);
        return groups;
    }

    public Group getByStudent(int studentId) {
        log.trace("getByStudent({})", studentId);
        Group group;
        try {
            group = jdbcTemplate.queryForObject(Query.GET_GROUPS_BY_STUDENT.getText(), groupMapper, studentId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_STUDENT, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_STUDENT, e);
        }
        log.debug("Found {} by {}", group, studentId);
        return group;
    }

    public List<Group> getByCourse(Integer courseId) {
        log.trace("getByCourse({})", courseId);
        List<Group> groups;
        try {
            groups = jdbcTemplate.query(Query.GET_GROUPS_BY_COURSE.getText(), groupMapper, courseId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_GET_BY_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_GET_BY_LESSON, e);
        }
        log.debug("Found {} by {}", groups, courseId);
        return groups;
    }

    public Group update(Group group) {
        log.trace("update({}).", group);
        try {
            jdbcTemplate.update(Query.UPDATE_GROUP.getText(), group.getName(), group.getId());
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_UPDATE_GROUP, e);
            throw new DaoException(MessagesConstants.CANNOT_UPDATE_GROUP, e);
        }
        log.debug("Group {} updated", group);
        return group;
    }

    public boolean assignToLesson(Integer lessonId, Integer groupId) {
        log.trace("assignToLesson({}, {})", lessonId, groupId);
        try {
            jdbcTemplate.update(Query.ASSIGN_GROUP_TO_LESSON.getText(), lessonId, groupId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_ASSIGN_GROUPS_TO_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_ASSIGN_GROUPS_TO_LESSON, e);
        }
        log.debug("Groups {} assigned to lesson {})", groupId, lessonId);
        return true;
    }

    public boolean deleteFromLesson(Integer lessonId, Integer groupId) {
        log.trace("deleteFromLesson({}, {})", lessonId, groupId);
        try {
            jdbcTemplate.update(Query.DELETE_GROUP_FROM_LESSON.getText(), lessonId, groupId);
        } catch (Exception e) {
            log.error(MessagesConstants.CANNOT_DELETE_GROUP_FROM_LESSON, e);
            throw new DaoException(MessagesConstants.CANNOT_DELETE_GROUP_FROM_LESSON, e);
        }
        log.debug("Group {} deleted from lesson {})", groupId, lessonId);
        return true;
    }
}