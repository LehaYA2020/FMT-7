package ru.fmt.university.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.fmt.university.rest.scenario.Create;
import ru.fmt.university.rest.scenario.Update;

import javax.validation.constraints.*;
import java.util.Objects;

@Getter
@Setter
public class Student {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private int id;
    @NotEmpty(message = "First name shouldn't be empty!")
    @Size(min = 2, max = 15, message = "First name should be between 2 and 15 characters!")
    private String firstName;
    @NotEmpty(message = "Last name shouldn't be empty!")
    @Size(min = 2, max = 25, message = "Last name should be between 2 and 25 characters!")
    private String lastName;
    @Min(value = 0, message = "GroupId should be greater then 0!")
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
