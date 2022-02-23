package ru.fmt.university.model.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int groupId;

    public Student(String firstName, String lastName, int groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public Student(int id, String firstName, String lastName, int groupId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public Student(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(groupId, student.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
