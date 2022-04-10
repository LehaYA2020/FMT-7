package ru.fmt.university.model.dto;

import lombok.*;
import ru.fmt.university.rest.scenario.Create;
import ru.fmt.university.rest.scenario.Update;

import javax.validation.constraints.*;
import java.util.Objects;

@Getter
@Setter
public class Group {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private int id;
    @NotEmpty(message = "Name shouldn't be empty!", groups = {Create.class, Update.class})
    @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters!")
    private String name;

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
