package ru.fmt.university.model.entity;

import ru.fmt.university.model.LessonType;

import javax.persistence.*;
import lombok.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "lessons")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;
    @Column(name = "classroom")
    private int classRoom;
    @Column(name = "day")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @Column(name = "time")
    private LocalTime startTime;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LessonType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lessons_groups",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<GroupEntity> groups;

    public LessonEntity(int id) {
        this.id = id;
    }

    public LessonEntity(int id, CourseEntity course, TeacherEntity teacher, int classRoom, DayOfWeek dayOfWeek, LocalTime startTime, LessonType type) {
        this.id = id;
        this.course = course;
        this.teacher = teacher;
        this.classRoom = classRoom;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.type = type;
    }

    public LessonEntity(CourseEntity course, TeacherEntity teacher, int classRoom, DayOfWeek dayOfWeek, LocalTime startTime, LessonType type) {
        this.course = course;
        this.teacher = teacher;
        this.classRoom = classRoom;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course, teacher, classRoom, dayOfWeek, startTime, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonEntity lessonEntity = (LessonEntity) o;
        return id == lessonEntity.id && classRoom == lessonEntity.classRoom && Objects.equals(course, lessonEntity.course) && Objects.equals(teacher, lessonEntity.teacher) && dayOfWeek == lessonEntity.dayOfWeek && Objects.equals(startTime, lessonEntity.startTime) && type == lessonEntity.type;
    }
}
