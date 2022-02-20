package ru.fmt.university.service.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.fmt.university.model.dto.Lesson;
import ru.fmt.university.model.dto.Teacher;
import ru.fmt.university.model.entity.CourseEntity;
import ru.fmt.university.model.entity.LessonEntity;
import ru.fmt.university.model.entity.TeacherEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class TeacherMapper implements RowMapper<Teacher> {
    @Override
    public Teacher mapRow(ResultSet rs, int i) throws SQLException {
        return new Teacher(rs.getInt("id"), rs.getString("first_name"),
                rs.getString("last_name"), rs.getInt("course_id"));
    }

    public Teacher toTeacher(TeacherEntity entity) {
        return new Teacher(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getCourse().getId());
    }

    public List<Teacher> toTeacher(List<TeacherEntity> entities) {
        return entities.stream().map(this::toTeacher).toList();
    }

    public TeacherEntity toEntity (Teacher teacher) {
        return new TeacherEntity(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), new CourseEntity(teacher.getCourseId()));
    }

    public List<TeacherEntity> toEntity (List<Teacher> teachers) {
        return teachers.stream().map(this::toEntity).toList();
    }

    public Page<Teacher> toDtoPage(Page<TeacherEntity> entities) {
        List<Teacher> dtoList = entities.getContent().stream().map(this::toTeacher).toList();

        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}
