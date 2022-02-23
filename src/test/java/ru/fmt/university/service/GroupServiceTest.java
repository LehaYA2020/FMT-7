package ru.fmt.university.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.fmt.university.model.dto.Group;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GroupServiceTest extends ServiceTest {

    @Test
    public void create_shouldCallGroupRepositoryCreateMethod() {
        when(groupRepository.save(groupMapper.toEntityForCreation(expectedGroup))).thenReturn(groupMapper.toEntity(expectedGroup));
        groupService.create(expectedGroup);
        verify(groupRepository).save(groupMapper.toEntityForCreation(expectedGroup));
    }

    @Test
    public void getAll_shouldCallGroupRepositoryGetAllMethod() {
        Pageable pageable = PageRequest.of(0, 10);
        when(groupRepository.findAll(pageable)).thenReturn(new PageImpl<>(groupMapper.toEntity(expectedGroups), pageable, pageable.getPageSize()));
        Page<Group> page = groupService.getAll(pageable);
        List<Group> actualGroups = page.getContent();

        verify(groupRepository).findAll(pageable);
        assertEquals(expectedGroups, actualGroups);
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
