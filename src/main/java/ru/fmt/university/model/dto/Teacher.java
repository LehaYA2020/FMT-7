package ru.fmt.university.model.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
public class Teacher {
    private int id;
    private String firstName;
    private String lastName;
    private int courseId;

    public Teacher(int id, String firstName, String lastName, int courseId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseId = courseId;
    }

    public Teacher(int id) {
        this.id = id;
    }

    public Teacher(String firstName, String lastName, int courseId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id && Objects.equals(firstName, teacher.firstName) && Objects.equals(lastName, teacher.lastName) && Objects.equals(courseId, teacher.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, courseId);
    }
}
