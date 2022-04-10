package ru.fmt.university.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fmt.university.model.dto.Group;
import ru.fmt.university.rest.scenario.Create;
import ru.fmt.university.rest.scenario.Update;
import ru.fmt.university.service.GroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class    GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping(value = "/groups")
    public ResponseEntity<Group> create(@Validated(Create.class) @RequestBody Group group, BindingResult bindingResult) {
        Group created = groupService.create(group);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/groups")
    public ResponseEntity<Page<Group>> getAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        final Page<Group> groups = groupService.getAll(pageable);

        return groups.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping(value = "/groups/{id}")
    public ResponseEntity<Group> getById(@PathVariable(name = "id") int id) {
        final Group group = groupService.getById(id);

        return group != null
                ? new ResponseEntity<>(group, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/groups/{id}")
    public ResponseEntity<?> update(@Validated(Update.class) @RequestBody Group group) {
        final Group updated = groupService.update(group);
        return updated != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/groups/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = groupService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/groups", params = {"courseId"})
    public ResponseEntity<List<Group>> getByCourseId(@RequestParam(value = "courseId") int courseId) {
        final List<Group> groups = groupService.getByCourse(courseId);

        return !groups.isEmpty()
                ? new ResponseEntity<>(groups, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/groups", params = {"groupId", "courseId"})
    public ResponseEntity<?> assignGroupToCourse(@RequestParam(value = "groupId") int groupId, @RequestParam(value = "courseId") int courseId) {
        final boolean assigned = groupService.assignToCourse(groupId, courseId);
        return assigned
                ? new ResponseEntity<>(HttpStatus.ACCEPTED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(value = "/groups", params = {"groupId", "courseId"})
    public ResponseEntity<?> deleteGroupFromCourse(@RequestParam(value = "groupId") int groupId, @RequestParam(value = "courseId") int courseId) {
        final boolean deleted = groupService.deleteFromCourse(groupId, courseId);

        return deleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/groups", params = {"lessonId", "groupId"})
    public ResponseEntity<?> assignGroupToLesson(@RequestParam(value = "lessonId") int lessonId, @RequestParam(value = "groupId") int groupId) {
        final boolean assigned = groupService.assignToLesson(lessonId, groupId);

        return assigned
                ? new ResponseEntity<>(HttpStatus.ACCEPTED)
                : new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(value = "/groups", params = {"lessonId", "groupId"})
    public ResponseEntity<?> deleteGroupFromLesson(@RequestParam(value = "lessonId") int lessonId, @RequestParam(value = "groupId") int groupId) {
        final boolean deleted = groupService.deleteFromLesson(lessonId, groupId);

        return deleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/groups", params = {"studentId"})
    public ResponseEntity<Group> getByStudentId(@RequestParam(value = "studentId") int studentId) {
        Group group = groupService.getByStudent(studentId);
        return group != null
                ? new ResponseEntity<>(group, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/groups", params = {"lessonId"})
    public ResponseEntity<List<Group>> getByLessonId(@RequestParam(value = "lessonId") int lessonId) {
        List<Group> groups = groupService.getByLesson(lessonId);
        return !groups.isEmpty()
                ? new ResponseEntity<>(groups, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
