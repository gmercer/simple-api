package com.github.capm.mapper;

import com.github.capm.entity.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String groupName = rs.getString("group_name");

        return new Group(id, groupName);
    }
}
