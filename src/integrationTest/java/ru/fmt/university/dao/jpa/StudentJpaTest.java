package ru.fmt.university.dao.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.fmt.university.dao.RepositoryTest;
import ru.fmt.university.model.entity.StudentEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest(properties = {"daoImpl=jpa"})
public class StudentJpaTest extends RepositoryTest {
    private static final StudentEntity FOR_CREATION = new StudentEntity("S-05", "Student", testGroupList.get(0));

    @Test
    public void create() {
        studentJpa.save(FOR_CREATION);
        assertNotEquals(testStudentList, studentJpa.findAll(PageRequest.of(0, 10)).getContent());
        FOR_CREATION.setId(5);
        assertEquals(Optional.of(FOR_CREATION), studentJpa.findById(5));
    }

    @Test
    public void getAll_shouldReturnAllStudentsFromDb() {
        assertEquals(testStudentList, studentJpa.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void getByID_shouldReturnStudentByIdFromDb() {
        assertEquals(Optional.of(testStudentList.get(0)), studentJpa.findById(1));
    }

    @Test
    public void delete_shouldDeleteFromDb() {
        studentJpa.deleteById(1);
        assertEquals(testStudentList.subList(1, 4), studentJpa.findAll(PageRequest.of(0, 10)).getContent());
    }

    @Test
    public void update_shouldUpdateStudent() {
        StudentEntity forUpdate = new StudentEntity(1, "S-01", "UPDATED", testGroupList.get(0));
        studentJpa.save(forUpdate);
        assertEquals(Optional.of(forUpdate), studentJpa.findById(1));
    }

    @Test
    public void assignToGroup_shouldAssignToGroup() {
        studentJpa.assignToGroup(testStudentList.get(3).getId(), testGroupList.get(2).getId());
        testStudentList.get(3).setGroup(testGroupList.get(2));
        assertEquals(testStudentList.subList(3, 4), studentJpa.findByGroup_Id(3));
        testStudentList.get(3).setGroup(null);
    }

    @Test
    public void updateGroupAssignment_shouldUpdateGroupAssignment() {
        studentJpa.updateGroupAssignment(testStudentList.get(1).getId(), testGroupList.get(1).getId());
        testStudentList.get(1).setGroup(testGroupList.get(1));
        assertEquals(testStudentList.subList(1, 2).get(0), studentJpa.findByGroup_Id(2).get(0));
        testStudentList.get(1).setGroup(testGroupList.get(0));
    }

    @Test
    public void getByGroup_shouldReturnStudentsByGroup() {
        assertEquals(testStudentList.subList(0, 2), studentJpa.findByGroup_Id(1));
    }

    @Test
    public void deleteFromGroup_shouldDeleteFromGroup() {
        studentJpa.deleteFromGroup(testStudentList.get(0).getId(), testGroupList.get(0).getId());
        assertEquals(testStudentList.subList(1, 2), studentJpa.findByGroup_Id(1));
    }
}
