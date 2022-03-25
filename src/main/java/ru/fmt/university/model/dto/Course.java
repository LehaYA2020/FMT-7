package ru.fmt.university.model.dto;

import lombok.*;
import ru.fmt.university.rest.scenario.Create;
import ru.fmt.university.rest.scenario.Update;

import javax.validation.constraints.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Course {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private int id;
    @NotEmpty(message = "Name shouldn't b e empty!", groups = {Create.class})
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters!")
    private String name;
    private String description;

    public Course(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Course(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
