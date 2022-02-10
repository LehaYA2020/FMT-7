package ru.fmt.university.dao.jdbcTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.dto.Group;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=jdbc"})
public class GroupRepositoryJdbcTemplateImplTest extends RepositoryTest {
    private static final Group FOR_CREATION = new Group(4, "Group-4");

    @Test
    public void create() {
        groupRepository.create(FOR_CREATION);
        assertNotEquals(testGroupList, groupRepository.getAll());
        assertEquals(FOR_CREATION, groupRepository.getById(FOR_CREATION.getId()));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> groupRepository.create(testGroupList.get(0)));

        assertEquals(MessagesConstants.CANNOT_INSERT_GROUPS, exception.getMessage());
    }

    @Test
    public void getAll_shouldReturnAllGroups() {
        assertEquals(testGroupList, groupRepository.getAll());
    }

    @Test
    public void getById() {
        assertEquals(testGroupList.get(0), groupRepository.getById(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> groupRepository.getById(10));

        assertEquals(MessagesConstants.CANNOT_GET_GROUP_BY_ID, exception.getMessage());
    }

    @Test
    public void assignToCourse() {
        groupRepository.assignToCourse(testGroupList.get(0).getId(), testCourseList.get(2).getId());
        assertEquals(testCourseList, courseRepository.getByGroupId(1));
    }

    @Test
    public void assignToLesson() {
        for (Group group : testGroupList.subList(1, 2)) {
            groupRepository.assignToLesson(1, group.getId());
        }
        assertEquals(lessonRepository.getAll(), lessonRepository.getByGroup(testGroupList.get(1).getId()));
    }

    @Test
    public void getByStudent() {
        assertEquals(testGroupList.get(0), groupRepository.getByStudent(1));
    }

    @Test
    public void delete() {
        groupRepository.delete(3);
        assertEquals(testGroupList.subList(0, 2), groupRepository.getAll());
    }

    @Test
    public void update() {
        Group expected = new Group(1, "updated");
        groupRepository.update(expected);

        assertEquals(expected, groupRepository.getById(1));
    }

    @Test
    public void deleteFromCourse() {
        groupRepository.deleteFromCourse(testGroupList.get(0).getId(), testCourseList.get(0).getId());
        assertEquals(testCourseList.subList(1, 2), courseRepository.getByGroupId(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testGroupList.subList(0, 2), groupRepository.getByLesson(lessonRepository.getById(2).getId()));
    }

    @Test
    public void getByCourse() {
        assertEquals(testGroupList.subList(0, 2), groupRepository.getByCourse(2));
    }

    @Test
    public void deleteFromLesson() {
        groupRepository.deleteFromLesson(2, testGroupList.get(0).getId());
        assertEquals(testGroupList.subList(1, 2), groupRepository.getByLesson(lessonRepository.getById(2).getId()));
    }
}
