package com.github.capm.mapper;

import com.github.capm.entity.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();

        role.setId(rs.getInt("id"));
        role.setUsername(rs.getString("username"));
        role.setRole(rs.getString("role"));

        return role;
    }
}
