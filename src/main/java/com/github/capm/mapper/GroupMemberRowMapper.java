package com.github.capm.mapper;

import com.github.capm.entity.GroupMember;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMemberRowMapper implements RowMapper<GroupMember> {
    @Override
    public GroupMember mapRow(ResultSet rs, int rowNum) throws SQLException {

        int id = rs.getInt("id");
        String username = rs.getString("username");
        int groupId = rs.getInt("group_id");

        return new GroupMember(id, username, groupId);
    }
}
