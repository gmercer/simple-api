package com.github.capm.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GrantedAuthorityRowMapper implements RowMapper<GrantedAuthority> {


    private String rolePrefix;

    public GrantedAuthorityRowMapper(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    @Override
    public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
        String roleName = getRolePrefix() + rs.getString(3);
        return new SimpleGrantedAuthority(roleName);
    }

    private String getRolePrefix() {
        return rolePrefix;
    }
}
