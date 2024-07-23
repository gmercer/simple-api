package com.github.capm.service;

import com.github.capm.dao.RoleDao;
import com.github.capm.entity.Role;
import com.github.capm.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleDao roleDao;

    @Override
    public List<Role> getRolesForUser(User user) {
        return roleDao.getRolesForUser(user);
    }

}
