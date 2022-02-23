package ru.fmt.university.dao.hibernate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.dao.exceptions.DaoException;
import ru.fmt.university.dao.exceptions.MessagesConstants;
import ru.fmt.university.model.entity.GroupEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"daoImpl=hibernate"})
public class GroupRepositoryHibernateImplTest extends RepositoryTest {
    private static final GroupEntity FOR_CREATION = new GroupEntity("Group-4");

    @Test
    public void create() {
        groupRepositoryHibernate.save(FOR_CREATION);
        assertNotEquals(testGroupList, groupRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent());
        FOR_CREATION.setId(4);
        assertEquals(Optional.of(FOR_CREATION), groupRepositoryHibernate.findById(FOR_CREATION.getId()));
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
        assertEquals(testGroupList, groupRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void getById() {
        assertEquals(Optional.of(testGroupList.get(0)), groupRepositoryHibernate.findById(1));
    }

    @Test
    public void getById_shouldReturnEmptyOptional() {
        assertTrue(groupRepositoryHibernate.findById(10).isEmpty());
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
        assertEquals(lessonRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent(), lessonRepositoryHibernate.findByGroups_id(testGroupList.get(1).getId()));
    }

    @Test
    public void getByStudent() {
        assertEquals(testGroupList.get(0), groupRepositoryHibernate.findByStudents_Id(1));
    }

    @Test
    public void delete() {
        groupRepositoryHibernate.deleteById(3);
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void update() {
        GroupEntity expected = new GroupEntity(1, "updated");
        groupRepositoryHibernate.save(expected);

        assertEquals(Optional.of(expected), groupRepositoryHibernate.findById(1));
    }

    @Test
    public void deleteFromCourse() {
        groupRepositoryHibernate.deleteFromCourse(testGroupList.get(0).getId(), testCourseList.get(0).getId());
        assertEquals(testCourseList.subList(1, 2), courseRepositoryHibernate.findByGroups_id(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.findByLessons_Id(2));
    }

    @Test
    public void getByCourse() {
        assertEquals(testGroupList.subList(0, 2), groupRepositoryHibernate.findByCourses_Id(2));
    }

    @Test
    public void deleteFromLesson() {
        groupRepositoryHibernate.deleteFromLesson(2, testGroupList.get(0).getId());
        assertEquals(testGroupList.subList(1, 2), groupRepositoryHibernate.findByLessons_Id(2));
    }
}
