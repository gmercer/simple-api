package com.github.capm.dao;

import com.github.capm.entity.Role;
import com.github.capm.entity.User;

import java.util.List;

public interface RoleDao {
    List<Role> getRolesForUser(User user);
}
