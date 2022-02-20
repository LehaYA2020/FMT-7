package ru.fmt.university.service.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.fmt.university.model.dto.Group;
import ru.fmt.university.model.entity.GroupEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Group(rs.getInt("id"), rs.getString("name"));
    }

    public Group toGroup(GroupEntity entity) {
        return new Group(entity.getId(), entity.getName());
    }

    public GroupEntity toEntity(Group group) {
        return new GroupEntity(group.getId(), group.getName());
    }

    public List<GroupEntity> toEntity(List<Group> groups) {
        return groups.stream().map(this::toEntity).toList();
    }

    public Page<Group> toDtoPage(Page<GroupEntity> entities) {
        List<Group> dtoList = entities.getContent().stream().map(this::toGroup).toList();
        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }

    public List<Group> toGroup(List<GroupEntity> entities) {
        return entities.stream().map(this::toGroup).toList();
    }
}
