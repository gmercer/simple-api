package com.github.capm.mapper;

import com.github.capm.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        String username = (rs.getString("username"));
        String password = (rs.getString("password"));
        Boolean enabled = (rs.getBoolean("enabled"));

        return new User(username, password, enabled);
    }
}
