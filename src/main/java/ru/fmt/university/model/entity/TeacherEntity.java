package ru.fmt.university.model.entity;

import javax.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "teachers")
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @OneToMany(mappedBy = "teacher")
    List<LessonEntity> lessons;



    public TeacherEntity(int id, String firstName, String lastName, CourseEntity course) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }

    public TeacherEntity(int id) {
        this.id = id;
    }

    public TeacherEntity(String firstName, String lastName, CourseEntity course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherEntity teacherEntity = (TeacherEntity) o;
        return id == teacherEntity.id && Objects.equals(firstName, teacherEntity.firstName) && Objects.equals(lastName, teacherEntity.lastName) && Objects.equals(course, teacherEntity.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, course);
    }
}
