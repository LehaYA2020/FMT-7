package ru.fmt.university.model.entity;

import ru.fmt.university.model.LessonType;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "lessons_groups",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<GroupEntity> groups;

    public LessonEntity(int id) {
        this.id = id;
    }

    public LessonEntity() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    public int getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(int classRoom) {
        this.classRoom = classRoom;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
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
