package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.entity.GroupEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(properties = {"daoImpl=jpa"})
public class GroupJpaTest extends RepositoryTest {
    private static final GroupEntity FOR_CREATION = new GroupEntity("Group-4");

    @Test
    public void create() {
        groupJpa.save(FOR_CREATION);
        assertNotEquals(testGroupList, groupJpa.findAll());
        FOR_CREATION.setId(4);
        assertEquals(FOR_CREATION, groupJpa.getById(FOR_CREATION.getId()));
    }

    @Test
    public void getAll_shouldReturnAllGroups() {
        assertEquals(testGroupList, groupJpa.findAll());
    }

    @Test
    public void getById() {
        assertEquals(testGroupList.get(0), groupJpa.getById(1));
    }

    @Test
    public void assignToCourse() {
        groupJpa.assignToCourse(testGroupList.get(0).getId(), testCourseList.get(2).getId());
        assertEquals(testCourseList, courseJpa.findByGroups_id(1));
    }

    @Test
    public void assignToLesson() {
        for (GroupEntity group : testGroupList.subList(1, 2)) {
            groupJpa.assignToLesson(1, group.getId());
        }
        assertEquals(lessonJpa.findAll(), lessonJpa.findByGroups_id(testGroupList.get(1).getId()));
    }

    @Test
    public void getByStudent() {
        assertEquals(testGroupList.get(0), groupJpa.findByStudents_Id(1));
    }

    @Test
    public void delete() {
        groupJpa.deleteById(3);
        assertEquals(testGroupList.subList(0, 2), groupJpa.findAll());
    }

    @Test
    public void update() {
        GroupEntity expected = new GroupEntity(1, "updated");
        groupJpa.save(expected);

        assertEquals(expected, groupJpa.getById(1));
    }

    @Test
    public void deleteFromCourse() {
        groupJpa.deleteFromCourse(1, 1);
        assertEquals(testCourseList.subList(1, 2), courseJpa.findByGroups_id(1));
    }

    @Test
    public void getByLesson() {
        assertEquals(testGroupList.subList(0, 2), groupJpa.findByLessons_Id(2));
    }

    @Test
    public void getByCourse() {
        assertEquals(testGroupList.subList(0, 2), groupJpa.findByCourses_Id(2));
    }

    @Test
    public void deleteFromLesson() {
        groupJpa.deleteFromLesson(2, testGroupList.get(0).getId());
        assertEquals(testGroupList.subList(1, 2), groupJpa.findByLessons_Id(2));
    }
}
