package com.github.capm.service;

import com.github.capm.entity.Role;
import com.github.capm.entity.User;

import java.util.List;

public interface RoleService {

    List<Role> getRolesForUser(User user);

}
