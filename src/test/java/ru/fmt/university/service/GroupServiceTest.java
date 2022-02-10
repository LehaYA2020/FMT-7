package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import ru.fmt.university.model.dto.Group;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GroupServiceTest extends ServiceTest {

    @Test
    public void create_shouldCallGroupRepositoryCreateMethod() {
        groupService.create(expectedGroup);
        verify(groupRepository).create(expectedGroup);
    }

    @Test
    public void getAll_shouldCallGroupRepositoryGetAllMethod() {
        when(groupRepository.getAll()).thenReturn(expectedGroups);
        List<Group> actualGroup = groupService.getAll();

        verify(groupRepository).getAll();
        assertEquals(expectedGroups, actualGroup);
    }

    @Test
    public void getById_shouldCallGroupRepositoryGetByIdMethod() {
        when(groupRepository.getById(1)).thenReturn(expectedGroup);
        Group actualGroup = groupService.getById(1);

        verify(groupRepository).getById(1);
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    public void update_shouldCallGroupRepositoryUpdateMethod() {
        when(groupRepository.update(expectedGroup)).thenReturn(expectedGroup);
        Group updatedGroup = groupService.update(expectedGroup);

        verify(groupRepository).update(expectedGroup);
        assertEquals(expectedGroup, updatedGroup);
    }

    @Test
    public void delete_shouldCallGroupRepositoryDeleteMethod() {
        groupService.delete(1);
        verify(groupRepository).delete(1);
    }

    @Test
    public void getByCourse_shouldCallGroupRepositoryGetByCourseMethod() {
        when(groupRepository.getByCourse(expectedCourse.getId())).thenReturn(expectedGroups);
        List<Group> actualGroups = groupService.getByCourse(expectedCourse.getId());

        verify(groupRepository).getByCourse(expectedCourse.getId());
        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    public void getByStudent_shouldCallGroupRepositoryGetByStudentMethod() {
        when(groupRepository.getByStudent(expectedStudent.getId())).thenReturn(expectedGroup);
        Group actualGroup = groupService.getByStudent(expectedStudent.getId());

        verify(groupRepository).getByStudent(expectedStudent.getId());
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    public void getByLesson_shouldCallGroupRepositoryGetByLessonMethod() {
        when(groupRepository.getByLesson(expectedLesson.getId())).thenReturn(expectedGroups);
        List<Group> actualGroups = groupService.getByLesson(expectedLesson.getId());

        verify(groupRepository).getByLesson(expectedLesson.getId());
        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    public void assignToCourse_shouldCallGroupRepositoryAssignToCourseMethod() {
        groupService.assignToCourse(expectedGroup.getId(), expectedCourse.getId());
        verify(groupRepository).assignToCourse(expectedGroup.getId(), expectedCourse.getId());
    }

    @Test
    public void deleteFromCourse_shouldCallGroupRepositoryDeleteFromCourseMethod() {
        groupService.deleteFromCourse(expectedGroup.getId(), expectedCourse.getId());
        verify(groupRepository).deleteFromCourse(expectedGroup.getId(), expectedCourse.getId());
    }

    @Test
    public void assignToLesson_shouldCallGroupRepositoryAssignToLessonMethod() {
        for (Group group : expectedGroups) {
            groupService.assignToLesson(expectedLesson.getId(), group.getId());
        }

        for (Group group : expectedGroups) {
            verify(groupRepository).assignToLesson(expectedLesson.getId(), group.getId());
        }
    }

    @Test
    public void deleteFromLesson_shouldCallGroupRepositoryDeleteFromLessonMethod() {
        groupService.deleteFromLesson(expectedLesson.getId(), expectedGroup.getId());
        verify(groupRepository).deleteFromLesson(expectedLesson.getId(), expectedGroup.getId());
    }
}
