package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.dto.Group;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class GroupRepositoryHibernateImplTest extends RepositoryTest {
    private static final Group FOR_CREATION = new Group("Group-4");

    @Test
    public void create() {
        groupRepositoryHibernate.create(FOR_CREATION);
        assertNotEquals(testGroupList, groupRepositoryHibernate.getAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, groupRepositoryHibernate.getById(FOR_CREATION.getId()));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        Group group = new Group("Group-1");
        Throwable exception = assertThrows(DaoException.class,
                () -> groupRepositoryHibernate.create(group));

        assertEquals(MessagesConstants.CANNOT_INSERT_GROUPS, exception.getMessage());
    }

    @Test
    public void getAll_shouldReturnAllGroups() {
        assertEquals(testGroupList, groupRepositoryHibernate.getAll());
    }

    @Test
    public void getById() {
        assertEquals(testGroupList.get(0), groupRepositoryHibernate.getById(1));
    }

    @Test
    public void getById_shouldThrowDaoException() {
        Throwable exception = assertThrows(DaoException.class,
                () -> groupRepositoryHibernate.getById(10));

        assertEquals(MessagesConstants.CANNOT_GET_GROUP_BY_ID, exception.getMessage());
    }

    @Test
    public void assignToCourse() {
        groupRepositoryHibernate.assignToCourse(testGroupList.get(0).getId(), testCourseList.get(2).getId());
        assertEquals(testCourseList, courseRepositoryHibernate.getByGroupId(1));
    }

    @Test
    public void assignToLesson() {
        for (Group group : testGroupList.subList(1, 2)) {
            groupRepositoryHibernate.assignToLesson(1, group.getId());
        }
        assertEquals(lessonRepositoryHibernate.getAll(), lessonRepositoryHibernate.getByGroup(testGroupList.get(1).getId()));
    }

    @Test
    public void getByStudent() {
        assertEquals(testGroupList.get(0), groupRepositoryHibernate.getByStudent(1));
    }

    @Test
    public void delete() {
        groupRepositoryHibernate.delete(3);
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.getAll());
    }

    @Test
    public void update() {
        Group expected = new Group(1, "updated");
        groupRepositoryHibernate.update(expected);

        assertEquals(expected, groupRepositoryHibernate.getById(1));
    }

    @Test
    public void deleteFromCourse() {
        groupRepositoryHibernate.deleteFromCourse(testGroupList.get(0).getId(), testCourseList.get(0).getId());
        assertEquals(testCourseList.subList(1, 2), courseRepositoryHibernate.getByGroupId(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.getByLesson(lessonRepositoryHibernate.getById(2).getId()));
    }

    @Test
    public void getByCourse() {
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.getByCourse(2));
    }

    @Test
    public void deleteFromLesson() {
        groupRepositoryHibernate.deleteFromLesson(2, testGroupList.get(0).getId());
        assertEquals(testGroupList.subList(1, 2), groupRepositoryHibernate.getByLesson(lessonRepositoryHibernate.getById(2).getId()));
    }
}
