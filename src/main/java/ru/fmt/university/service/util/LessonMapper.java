package ru.fmt.university.service.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.fmt.university.model.LessonType;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.entity.CourseEntity;
import ru.fmt.university.model.entity.LessonEntity;
import ru.fmt.university.model.entity.TeacherEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.List;

@Service
public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Lesson(rs.getInt("id"), rs.getInt("course_id"),
                rs.getInt("teacher_id"), rs.getInt("classroom"),
                DayOfWeek.valueOf(rs.getString("day")), rs.getTime("time").toLocalTime(),
                LessonType.valueOf(rs.getString("type")));
    }

    public LessonEntity toEntity(Lesson lesson) {
        return new LessonEntity(lesson.getId(), new CourseEntity(lesson.getCourseId())
                , new TeacherEntity(lesson.getTeacherId()), lesson.getClassRoom()
                , lesson.getDayOfWeek(), lesson.getStartTime(), lesson.getType());
    }

    public LessonEntity toEntityForCreation(Lesson lesson) {
        return new LessonEntity(new CourseEntity(lesson.getCourseId())
                , new TeacherEntity(lesson.getTeacherId()), lesson.getClassRoom()
                , lesson.getDayOfWeek(), lesson.getStartTime(), lesson.getType());
    }

    public List<LessonEntity> toEntity(List<Lesson> lessons) {
        return lessons.stream().map(this::toEntity).toList();
    }

    public Lesson toLesson(LessonEntity entity) {
        return new Lesson(entity.getId(), entity.getCourse().getId(), entity.getTeacher().getId(),
                entity.getClassRoom(), entity.getDayOfWeek(), entity.getStartTime(), entity.getType());
    }

    public List<Lesson> toLesson(List<LessonEntity> entities) {
        return entities.stream().map(this::toLesson).toList();
    }

    public Page<Lesson> toDtoPage(Page<LessonEntity> entities) {
        List<Lesson> dtoList = entities.getContent().stream().map(this::toLesson).toList();

        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}
