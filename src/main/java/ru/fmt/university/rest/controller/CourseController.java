package ru.fmt.university.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fmt.university.model.dto.Course;
import ru.fmt.university.rest.scenario.Create;
import ru.fmt.university.rest.scenario.Update;
import ru.fmt.university.service.CourseService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/courses")
    public ResponseEntity<Course> create(@Validated(Create.class) @RequestBody Course course, BindingResult bindingResult) {
        Course created = courseService.create(course);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/courses")
    public ResponseEntity<Page<Course>> getAll(@PageableDefault(sort = {"id"}) Pageable pageable) {
        final Page<Course> courses = courseService.getAll(pageable);

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

    @GetMapping(value = "/courses", params = {"groupId"})
    public ResponseEntity<Collection<Course>> getByGroupId(@RequestParam(value = "groupId") int groupId) {
        final Collection<Course> courses = courseService.getByGroupId(groupId);

        return !courses.isEmpty()
                ? new ResponseEntity<>(courses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/courses/{id}")
    public ResponseEntity<?> update(@Validated(Update.class) @RequestBody Course course) {
        final Course updatedCourse = courseService.update(course);
        return updatedCourse != null
                ? new ResponseEntity<>(course, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/courses/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = courseService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
