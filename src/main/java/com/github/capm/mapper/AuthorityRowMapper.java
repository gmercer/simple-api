package com.github.capm.mapper;

import com.github.capm.entity.Authority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorityRowMapper implements RowMapper<Authority> {

    @Override
    public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {

        String username = rs.getString("username");
        String authority = rs.getString("authority");

        return new Authority(username, authority);
    }
}
