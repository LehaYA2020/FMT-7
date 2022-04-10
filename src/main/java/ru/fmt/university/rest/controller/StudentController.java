package ru.fmt.university.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.fmt.university.model.dto.Student;
import ru.fmt.university.rest.scenario.Create;
import ru.fmt.university.rest.scenario.Update;
import ru.fmt.university.service.StudentService;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/students")
    public ResponseEntity<?> create(@Validated(Create.class) @RequestBody Student student) {
        studentService.create(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/students")
    public ResponseEntity<Page<Student>> getAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        final Page<Student> students = studentService.getAll(pageable);

        return students.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getById(@PathVariable(name = "id") int id) {
        final Student student = studentService.getById(id);

        return student != null
                ? new ResponseEntity<>(student, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/students/{id}")
    public ResponseEntity<?> update(@Validated(Update.class) @RequestBody Student student) {
        final Student updated = studentService.update(student);
        return updated != null
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/students/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = studentService.deleteById(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/students", params = {"groupId"})
    public ResponseEntity<List<Student>> getByGroupId(@RequestParam(value = "groupId") int groupId) {
        final List<Student> students = studentService.getByGroup(groupId);

        return !students.isEmpty()
                ? new ResponseEntity<>(students, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/students", params = {"studentId","groupId"})
    public ResponseEntity<?> assignStudentToGroup(@RequestParam(value = "studentId") int studentId, @RequestParam(value = "groupId") int groupId) {
        final boolean assigned = studentService.assignStudentToGroup(studentId, groupId);

        return assigned
                ? new ResponseEntity<>(HttpStatus.ACCEPTED)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
