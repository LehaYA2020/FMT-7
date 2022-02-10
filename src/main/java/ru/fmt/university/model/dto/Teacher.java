package ru.fmt.university.model.dto;

import java.util.Objects;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
