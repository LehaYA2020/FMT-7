package ru.fmt.university.model.dto;

import lombok.*;
import ru.fmt.university.model.LessonType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
public class Lesson {
    private int id;
    private int courseId;
    private int teacherId;
    private int classRoom;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
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
