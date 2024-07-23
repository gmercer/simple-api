package com.github.capm.dao;

import com.github.capm.entity.Group;
import com.github.capm.mapper.GroupRowMapper;
import com.github.capm.mapper.UserRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {

    NamedParameterJdbcTemplate template;

    public GroupDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Group> getGroupByName(String groupName) {
        return template.query("select * from groups where group_name = :group_name",
                new MapSqlParameterSource("group_name", groupName),
                new GroupRowMapper());
    }

    @Override
    public Group getGroupById(int id) {
        Group retval = null;
        List<Group> result = template.query("select * from groups where id = :id",
                new MapSqlParameterSource("id", id),
                new GroupRowMapper());
        if (!result.isEmpty()) {
            retval = result.get(0);
        }
        return retval;
    }

    @Override
    public List<Group> findAllGroups() {
           return template.query("select * from groups", new GroupRowMapper());
    }
}
