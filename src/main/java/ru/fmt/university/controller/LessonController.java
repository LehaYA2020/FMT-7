package ru.fmt.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.service.LessonService;

import java.util.List;

@RestController
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @PostMapping(value = "/lessons")
    public ResponseEntity<?> create(@RequestBody Lesson lesson) {
        lessonService.create(lesson);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/lessons")
    public ResponseEntity<Page<Lesson>> getAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        final Page<Lesson> lessons = lessonService.getAll(pageable);

        return lessons.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @GetMapping("/lessons/{id}")
    public ResponseEntity<Lesson> getById(@PathVariable(name = "id") int id) {
        final Lesson lesson = lessonService.getById(id);

        return lesson != null
                ? new ResponseEntity<>(lesson, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/lessons/{id}")
    public ResponseEntity<?> update(@RequestBody Lesson lesson) {
        final Lesson updated = lessonService.update(lesson);
        return updated != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/lessons/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = lessonService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/lessons", params = {"studentId"})
    public ResponseEntity<List<Lesson>> getLessonByStudent(@RequestParam(value = "studentId") int studentId) {
        final List<Lesson> lessons = lessonService.getLessonsByStudent(studentId);

        return !lessons.isEmpty()
                ? new ResponseEntity<>(lessons, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/lessons", params = {"groupId"})
    public ResponseEntity<List<Lesson>> getLessonByGroup(@RequestParam(value =   "groupId") int groupId) {
        final List<Lesson> lessons = lessonService.getLessonsByGroup(groupId);

        return !lessons.isEmpty()
                ? new ResponseEntity<>(lessons, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/lessons", params = {"teacherId"})
    public ResponseEntity<List<Lesson>> getLessonByTeacher(@RequestParam(value = "teacherId") int teacherId) {
        final List<Lesson> lessons = lessonService.getLessonsByTeacher(teacherId);

        return !lessons.isEmpty()
                ? new ResponseEntity<>(lessons, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/lessons", params = {"courseId"})
    public ResponseEntity<List<Lesson>> getLessonByCourse(@RequestParam(value = "courseId") int courseId) {
        final List<Lesson> lessons = lessonService.getLessonsByCourse(courseId);

        return !lessons.isEmpty()
                ? new ResponseEntity<>(lessons, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
