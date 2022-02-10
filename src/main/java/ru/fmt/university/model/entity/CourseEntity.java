package ru.fmt.university.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
public class CourseEntity {
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

    @OneToMany(mappedBy = "course")
    List<TeacherEntity> teachers;

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

    public CourseEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groupEntities) {
        this.groups = groupEntities;
    }

    public List<LessonEntity> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonEntity> lessonEntities) {
        this.lessons = lessonEntities;
    }

    public List<TeacherEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherEntity> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity courseEntity = (CourseEntity) o;
        return id == courseEntity.id && Objects.equals(name, courseEntity.name) && Objects.equals(description, courseEntity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
