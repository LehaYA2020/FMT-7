package ru.fmt.university.service.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.fmt.university.model.dto.Student;
import ru.fmt.university.model.entity.GroupEntity;
import ru.fmt.university.model.entity.StudentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(rs.getInt("id"), rs.getString("first_name"),
                rs.getString("last_name"), rs.getInt("group_id"));
    }

    public Student toStudent(StudentEntity entity) {
        if (entity.getGroup() != null) {
            return new Student(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getGroup().getId());
        } else {
            return new Student(entity.getId(), entity.getFirstName(), entity.getLastName(), 0);
        }
    }

    public List<Student> toStudent(List<StudentEntity> entities) {
        return entities.stream().map(this::toStudent).toList();
    }

    public StudentEntity toEntity(Student student) {
        return new StudentEntity(student.getId(), student.getFirstName(), student.getLastName(), new GroupEntity(student.getGroupId()));
    }

    public List<StudentEntity> toEntity(List<Student> students) {
        return students.stream().map(this::toEntity).toList();
    }

    public Page<Student> toDtoPage(Page<StudentEntity> entities) {
        List<Student> dtoList = entities.getContent().stream().map(this::toStudent).toList();

        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}
