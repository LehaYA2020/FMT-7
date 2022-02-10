package ru.fmt.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fmt.university.model.dto.Course;
import ru.fmt.university.service.ICourseService;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private ICourseService courseService;

    @PostMapping(value = "/courses")
    public ResponseEntity<?> create(@RequestBody Course course) {
        courseService.create(course);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAll() {
        final List<Course> courses = courseService.getAll();

        return courses.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping(value = "/courses/{id}")
    public ResponseEntity<Course> getById(@PathVariable(name = "id") int id) {
        final Course course = courseService.getById(id);

        return course != null
                ? new ResponseEntity<>(course, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/courses", params = {"{groupId}"})
    public ResponseEntity<List<Course>> getByGroupId(@RequestParam(value = "groupId") int groupId) {
        final List<Course> courses = courseService.getByGroupId(groupId);

        return !courses.isEmpty()
                ? new ResponseEntity<>(courses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/courses/{id}")
    public ResponseEntity<?> update(@RequestBody Course course) {
        final Course updatedCourse = courseService.update(course);
        return updatedCourse != null
                ? new ResponseEntity<>(course, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = courseService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
