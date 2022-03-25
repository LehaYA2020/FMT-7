package ru.fmt.university.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.fmt.university.rest.scenario.Create;
import ru.fmt.university.rest.scenario.Update;

import javax.validation.constraints.*;
import java.util.Objects;

@Getter
@Setter
public class Teacher {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private int id;
    @NotEmpty(message = "First name shouldn't be empty!")
    @Size(min = 2, max = 15, message = "First name should be between 2 and 15 characters!")
    private String firstName;
    @NotEmpty(message = "Last name shouldn't be empty!")
    @Size(min = 2, max = 25, message = "Last name should be between 2 and 25 characters!")
    private String lastName;
    @Min(value = 0, message = "CourseId should be greater then 0!")
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
