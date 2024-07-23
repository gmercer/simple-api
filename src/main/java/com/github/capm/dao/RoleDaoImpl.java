package com.github.capm.dao;

import com.github.capm.entity.Role;
import com.github.capm.entity.User;
import com.github.capm.mapper.RoleRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    NamedParameterJdbcTemplate template;

    public RoleDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Role> getRolesForUser(User user) {
        return template.query("select * from api.role where username = :username", new MapSqlParameterSource("username", user.getUsername()), new RoleRowMapper());
    }
}
