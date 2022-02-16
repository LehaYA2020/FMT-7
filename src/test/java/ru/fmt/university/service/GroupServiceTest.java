package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import ru.fmt.university.model.dto.Group;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GroupServiceTest extends ServiceTest {

    @Test
    public void create_shouldCallGroupRepositoryCreateMethod() {
        groupService.create(expectedGroup);
        verify(groupRepository).save(groupMapper.toEntity(expectedGroup));
    }

    @Test
    public void getAll_shouldCallGroupRepositoryGetAllMethod() {
        when(groupRepository.findAll()).thenReturn(groupMapper.toEntity(expectedGroups));
        List<Group> actualGroup = groupService.getAll();

        verify(groupRepository).findAll();
        assertEquals(expectedGroups, actualGroup);
    }

    @Test
    public void getById_shouldCallGroupRepositoryGetByIdMethod() {
        when(groupRepository.findById(1)).thenReturn(Optional.of(groupMapper.toEntity(expectedGroup)));
        Group actualGroup = groupService.getById(1);

        verify(groupRepository).findById(1);
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    public void update_shouldCallGroupRepositoryUpdateMethod() {
        when(groupRepository.save(groupMapper.toEntity(expectedGroup))).thenReturn(groupMapper.toEntity(expectedGroup));
        Group updatedGroup = groupService.update(expectedGroup);

        verify(groupRepository).save(groupMapper.toEntity(expectedGroup));
        assertEquals(expectedGroup, updatedGroup);
    }

    @Test
    public void delete_shouldCallGroupRepositoryDeleteMethod() {
        groupService.deleteById(1);
        verify(groupRepository).deleteById(1);
    }

    @Test
    public void getByCourse_shouldCallGroupRepositoryGetByCourseMethod() {
        when(groupRepository.findByCourses_Id(expectedCourse.getId())).thenReturn(groupMapper.toEntity(expectedGroups));
        List<Group> actualGroups = groupService.getByCourse(expectedCourse.getId());

        verify(groupRepository).findByCourses_Id(expectedCourse.getId());
        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    public void getByStudent_shouldCallGroupRepositoryGetByStudentMethod() {
        when(groupRepository.findByStudents_Id(expectedStudent.getId())).thenReturn(groupMapper.toEntity(expectedGroup));
        Group actualGroup = groupService.getByStudent(expectedStudent.getId());

        verify(groupRepository).findByStudents_Id(expectedStudent.getId());
        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    public void getByLesson_shouldCallGroupRepositoryGetByLessonMethod() {
        when(groupRepository.findByLessons_Id(expectedLesson.getId())).thenReturn(groupMapper.toEntity(expectedGroups));
        List<Group> actualGroups = groupService.getByLesson(expectedLesson.getId());

        verify(groupRepository).findByLessons_Id(expectedLesson.getId());
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
