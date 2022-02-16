package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.entity.GroupEntity;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"daoImpl=hibernate"})
public class GroupRepositoryHibernateImplTest extends RepositoryTest {
    private static final GroupEntity FOR_CREATION = new GroupEntity("Group-4");

    @Test
    public void create() {
        groupRepositoryHibernate.save(FOR_CREATION);
        assertNotEquals(testGroupList, groupRepositoryHibernate.findAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, groupRepositoryHibernate.getById(FOR_CREATION.getId()));
    }

    @Test
    public void create_shouldThrow_DaoException() {
        GroupEntity group = new GroupEntity("Group-1");
        Throwable exception = assertThrows(DaoException.class,
                () -> groupRepositoryHibernate.save(group));

        assertEquals(MessagesConstants.CANNOT_INSERT_GROUPS, exception.getMessage());
    }

    @Test
    public void getAll_shouldReturnAllGroups() {
        assertEquals(testGroupList, groupRepositoryHibernate.findAll());
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
        assertEquals(testCourseList, courseRepositoryHibernate.findByGroups_id(1));
    }

    @Test
    public void assignToLesson() {
        for (GroupEntity group : testGroupList.subList(1, 2)) {
            groupRepositoryHibernate.assignToLesson(1, group.getId());
        }
        assertEquals(lessonRepositoryHibernate.findAll(), lessonRepositoryHibernate.findByGroups_id(testGroupList.get(1).getId()));
    }

    @Test
    public void getByStudent() {
        assertEquals(testGroupList.get(0), groupRepositoryHibernate.findByStudents_Id(1));
    }

    @Test
    public void delete() {
        groupRepositoryHibernate.deleteById(3);
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.findAll());
    }

    @Test
    public void update() {
        GroupEntity expected = new GroupEntity(1, "updated");
        groupRepositoryHibernate.save(expected);

        assertEquals(expected, groupRepositoryHibernate.getById(1));
    }

    @Test
    public void deleteFromCourse() {
        groupRepositoryHibernate.deleteFromCourse(testGroupList.get(0).getId(), testCourseList.get(0).getId());
        assertEquals(testCourseList.subList(1, 2), courseRepositoryHibernate.findByGroups_id(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.findByLessons_Id(lessonRepositoryHibernate.getById(2).getId()));
    }

    @Test
    public void getByCourse() {
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.findByCourses_Id(2));
    }

    @Test
    public void deleteFromLesson() {
        groupRepositoryHibernate.deleteFromLesson(2, testGroupList.get(0).getId());
        assertEquals(testGroupList.subList(1, 2), groupRepositoryHibernate.findByLessons_Id(lessonRepositoryHibernate.getById(2).getId()));
    }
}
