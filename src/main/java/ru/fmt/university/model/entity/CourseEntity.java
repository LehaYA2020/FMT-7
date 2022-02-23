package ru.fmt.university.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "courses")
public class CourseEntity {
    @OneToMany(mappedBy = "course")
    List<TeacherEntity> teachers;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "courses")
    private List<GroupEntity> groups;
    @OneToMany(mappedBy = "course")
    private List<LessonEntity> lessons;

    public CourseEntity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CourseEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public CourseEntity(int id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }
}
