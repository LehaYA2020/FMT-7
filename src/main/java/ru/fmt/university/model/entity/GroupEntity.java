package ru.fmt.university.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "groups")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "groups_courses",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<CourseEntity> courses;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lessons_groups",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private List<LessonEntity> lessons;

    @OneToMany(mappedBy = "group")
    private List<StudentEntity> students;

    public GroupEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroupEntity(String name) {
        this.name = name;
    }

    public GroupEntity(int id) {
        this.id = id;
    }

    public GroupEntity(String name, List<CourseEntity> courses, List<LessonEntity> lessons, List<StudentEntity> students) {
        this.name = name;
        this.courses = courses;
        this.lessons = lessons;
        this.students = students;
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

    public List<LessonEntity> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonEntity> lessonEntities) {
        this.lessons = lessonEntities;
    }

    public List<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity groupEntity = (GroupEntity) o;
        return id == groupEntity.id && Objects.equals(name, groupEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                ", lessons=" + lessons +
                ", students=" + students +
                '}';
    }
}
