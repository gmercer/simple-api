package com.github.capm.mapper;

import com.github.capm.entity.GroupAuthority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupAuthorityRowMapper implements RowMapper<GroupAuthority> {

    @Override
    public GroupAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {

        int groupId = rs.getInt("group_id");
        String authority = rs.getString("authority");

        return new GroupAuthority(groupId, authority);
    }
}
