package com.github.capm.dao;

import com.github.capm.entity.User;
import com.github.capm.mapper.UserRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    NamedParameterJdbcTemplate template;

    public UserDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public User getUserByName(String name) {
        User retval = null;
        List<User> results = template.query("select * from users where username = :username",
                new MapSqlParameterSource("username", name),
                new UserRowMapper());
        if (!results.isEmpty()) {
            retval = results.get(0);
        }
        return retval;
    }

    @Override
    public List<User> getUsers() {
        return template.query("select * from users", new UserRowMapper());
    }

}
