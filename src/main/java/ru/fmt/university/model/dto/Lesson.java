package ru.fmt.university.model.dto;

import lombok.*;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.rest.scenario.Create;
import ru.fmt.university.rest.scenario.Update;

import javax.validation.constraints.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
public class Lesson {
    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    private int id;
    @NotNull(groups = Create.class)
    @Min(value = 0, message = "courseId should be greater then 0!")
    private int courseId;
    @NotNull(groups = Create.class)
    @Min(value = 0, message = "teacherId should be greater then 0!")
    private int teacherId;
    @NotNull(groups = Create.class)
    @Min(value = 0, message = "classRoom should be greater then 0!")
    private int classRoom;
    @NotNull(groups = Create.class)
    private DayOfWeek dayOfWeek;
    @NotNull(groups = Create.class)
    private LocalTime startTime;
    @NotNull(groups = Create.class)
    private LessonType type;

    public Lesson(int id, int courseId, int teacherId, int classRoom, DayOfWeek dayOfWeek, LocalTime startTime, LessonType type) {
        this.id = id;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.classRoom = classRoom;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.type = type;
    }

    public Lesson(int id) {
        this.id = id;
    }

    public Lesson(int courseId, int teacherId, int classRoom, DayOfWeek dayOfWeek, LocalTime startTime, LessonType type) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.classRoom = classRoom;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, teacherId, classRoom, dayOfWeek, startTime, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id && classRoom == lesson.classRoom && Objects.equals(courseId, lesson.courseId) && Objects.equals(teacherId, lesson.teacherId) && dayOfWeek == lesson.dayOfWeek && Objects.equals(startTime, lesson.startTime) && type == lesson.type;
    }
}
