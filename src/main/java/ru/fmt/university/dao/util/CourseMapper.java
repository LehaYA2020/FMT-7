package ru.fmt.university.dao.util;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.fmt.university.model.dto.Course;
import ru.fmt.university.model.entity.CourseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class CourseMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
    }

    public Course toCourse(CourseEntity entity) {
        return new Course(entity.getId(), entity.getName(), entity.getDescription());
    }
    public CourseEntity toEntity(Course course) {
        return new CourseEntity(course.getId(), course.getName(), course.getDescription());
    }

    public List<Course> toCourse(List<CourseEntity> entities) {
        return entities.stream().map(e->toCourse(e)).toList();
    }
}
